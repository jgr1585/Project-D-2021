package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BillingService;
import fhv.teamd.hotel.application.OrganizationService;
import fhv.teamd.hotel.application.dto.BillDTO;
import fhv.teamd.hotel.application.dto.BillEntryDTO;
import fhv.teamd.hotel.application.dto.OrganizationDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.view.forms.InvoiceForm;
import fhv.teamd.hotel.view.forms.subForms.BillAssignmentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("invoice")
public class InvoiceController {

    @Autowired
    private BillingService billingService;

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("billList")
    public ModelAndView billList(
            @ModelAttribute InvoiceForm invoiceForm,
            @RequestParam(required = false) String action,
            Model model) throws InvalidIdException {

        BillDTO billDTO = this.billingService.getBill(invoiceForm.getStayId());
        model.addAttribute("bill", billDTO);

        List<Boolean> selectedCheckboxStates = invoiceForm.getCheckboxStates();
        if (action == null) {
            for (BillEntryDTO ignored : billDTO.entries()) {
                selectedCheckboxStates.add(false);
            }
        }

        model.addAttribute("invoiceForm", invoiceForm);

        return new ModelAndView("/invoice/billList");
    }

    @PostMapping("billList")
    public RedirectView submitBillList(
            @ModelAttribute InvoiceForm invoiceForm,
            @RequestParam(required = false) String action,
            @RequestParam(name = "billEntryChecks", required = false) List<Integer> billEntryChecks,
            RedirectAttributes redirectAttributes) throws InvalidIdException {

        if (action == null || action.equals("next")) {
            BillDTO billDTO = this.billingService.getBill(invoiceForm.getStayId());

            List<BillEntryDTO> billEntryDTOList = billDTO.entries();

            List<Boolean> selectedCheckboxStates = invoiceForm.getCheckboxStates();
            for (BillEntryDTO ignored : billEntryDTOList) {
                selectedCheckboxStates.add(false);
            }

//            List<BillEntryDTO> selectedBillEntries = invoiceForm.getSelectedBillEntries();
            if (billEntryChecks != null) {
                for (Integer index: billEntryChecks) {
//                    selectedBillEntries.add(billEntryDTOList.get(index));
                    selectedCheckboxStates.set(index, true);
                }
            }


            String organizationId = invoiceForm.getOrganizationId();
            if (organizationId != null && !organizationId.equals("")) {
                Optional<OrganizationDTO> orgResult = this.organizationService.findOrganizationById(invoiceForm.getOrganizationId());

                if (orgResult.isEmpty()) {
                    // should not happen normally
                    return new RedirectView("/");
                }

                OrganizationDTO organizationDTO = orgResult.get();
                invoiceForm.setDiscountPercent(organizationDTO.discount());
            }
        }

        redirectAttributes.addFlashAttribute("invoiceForm", invoiceForm);

        return new RedirectView("billEdit");
    }

    @GetMapping("billEdit")
    public ModelAndView billEdit(
            @ModelAttribute InvoiceForm invoiceForm,
            Model model) {

        model.addAttribute("invoiceForm", invoiceForm);

        return new ModelAndView("/invoice/billEdit");
    }

    @PostMapping("billEdit")
    public RedirectView submitBillEdit(
            @ModelAttribute InvoiceForm invoiceForm,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("invoiceForm", invoiceForm);

        if (action.equals("prev")) {
            return new RedirectView("billList?stayId=" + invoiceForm.getStayId());
        }

        return new RedirectView("billView");
    }

    @GetMapping("billView")
    public ModelAndView billView(
            @ModelAttribute InvoiceForm invoiceForm,
            Model model) throws InvalidIdException {

        BillDTO billDTO = this.billingService.getBill(invoiceForm.getStayId());
        model.addAttribute("bill", billDTO);

        List<Boolean> selectedCheckboxStates = invoiceForm.getCheckboxStates();
        List<BillEntryDTO> billEntryDTOList = billDTO.entries();

        double subTotal = 0;
        for (int i = 0; i < billEntryDTOList.size(); i++) {
            if (selectedCheckboxStates.get(i)) {
                subTotal += billEntryDTOList.get(i).subTotal();
            }
        }

        double discountTotal = (subTotal / 100) * 0;
        double invoiceTotal = subTotal - discountTotal;

        double taxAmount = invoiceTotal * 0.2;
        double invoiceTotalTax = invoiceTotal + taxAmount;

        invoiceForm.setSubTotal(subTotal);
        invoiceForm.setInvoiceTotal(invoiceTotal);
        invoiceForm.setTaxAmount(taxAmount);
        invoiceForm.setInvoiceTotalTax(invoiceTotalTax);

        model.addAttribute("invoiceForm", invoiceForm);

        return new ModelAndView("/invoice/billView");
    }

    @PostMapping("billView")
    public RedirectView submitBillView(
            @ModelAttribute InvoiceForm invoiceForm,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) throws InvalidIdException {

        redirectAttributes.addFlashAttribute("invoiceForm", invoiceForm);

        if (action.equals("prev")) {
            return new RedirectView("billEdit");
        }

        BillDTO billDTO = this.billingService.getBill(invoiceForm.getStayId());

        List<Boolean> selectedCheckboxStates = invoiceForm.getCheckboxStates();
        List<BillEntryDTO> billEntryDTOList = billDTO.entries();

        for (int i = 0; i < billEntryDTOList.size(); i++) {
            if (selectedCheckboxStates.get(i)) {
                final String desc = billEntryDTOList.get(i).description();

                BillAssignmentForm billAssignmentForm = invoiceForm.getBillAssignmentForm();
                RepresentativeDetails representativeDetails = new RepresentativeDetails(
                        billAssignmentForm.getFirstName(),
                        billAssignmentForm.getLastName(),
                        billAssignmentForm.getMail(),
                        new Address(
                                billAssignmentForm.getStreet(),
                                billAssignmentForm.getZip(),
                                billAssignmentForm.getCity(),
                                billAssignmentForm.getCountry()
                        ),
                        billAssignmentForm.getPhone(),
                        billAssignmentForm.getCreditCardNumber(),
                        billAssignmentForm.getPaymentMethod()
                );

                this.billingService.assignPayments(billDTO.id(), e -> e.description().equals(desc), representativeDetails);
            }
        }

        return new RedirectView("billList?stayId=" + invoiceForm.getStayId());
    }
}

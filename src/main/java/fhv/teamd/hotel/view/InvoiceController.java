package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BillingService;
import fhv.teamd.hotel.application.dto.BillDTO;
import fhv.teamd.hotel.application.dto.BillEntryDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.view.forms.InvoiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;

@Controller
@RequestMapping("invoice")
public class InvoiceController {

    @Autowired
    private BillingService billingService;

    @GetMapping("billList")
    public ModelAndView billList(
            @RequestParam String stayId,
            @RequestParam(required = false) String action,
            @ModelAttribute InvoiceForm invoiceForm,
            Model model) throws InvalidIdException {

        BillDTO billDTO = this.billingService.getBill(stayId);
        model.addAttribute("bill", billDTO);

        List<Boolean> selectedBills = invoiceForm.getSelectedBills();
        if (action == null) {
            for (BillEntryDTO ignored : billDTO.entries()) {
                selectedBills.add(false);
            }
        }

        model.addAttribute("invoiceForm", invoiceForm);

        return new ModelAndView("/invoice/billList");
    }

    @PostMapping("billList")
    public RedirectView submitBillList(
            @ModelAttribute InvoiceForm invoiceForm,
            @RequestParam String stayId,
            @RequestParam(required = false) String action,
            @RequestParam(name = "billEntryChecks", required = false) List<Integer> billEntryChecks,
            RedirectAttributes redirectAttributes) throws InvalidIdException {

        if (action == null || action.equals("next")) {
            BillDTO billDTO = this.billingService.getBill(stayId);

            List<Boolean> selectedBills = invoiceForm.getSelectedBills();
            for (BillEntryDTO ignored : billDTO.entries()) {
                selectedBills.add(false);
            }

            if (billEntryChecks != null) {
                for (Integer index: billEntryChecks) {
                    selectedBills.set(index, true);
                }
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
            Model model) {

        model.addAttribute("invoiceForm", invoiceForm);

        return new ModelAndView("/invoice/billView");
    }

    @PostMapping("billView")
    public RedirectView submitBillView(
            @ModelAttribute InvoiceForm invoiceForm,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("invoiceForm", invoiceForm);

        if (action.equals("prev")) {
            return new RedirectView("billEdit");
        }

        return new RedirectView("billList?stayId=" + invoiceForm.getStayId());
    }
}

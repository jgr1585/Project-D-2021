package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BillingService;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.view.forms.InvoiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("invoice")
public class InvoiceController {

    @Autowired
    private BillingService billingService;

    @GetMapping("billList")
    public ModelAndView billList(
            @RequestParam String stayId,
            @ModelAttribute InvoiceForm invoiceForm,
            Model model) {

        try {
            model.addAttribute("bill", this.billingService.getBill(stayId));
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }

        model.addAttribute("invoiceForm", invoiceForm);

        return new ModelAndView("/invoice/billList");
    }

    @PostMapping("billList")
    public RedirectView submitBillList(
            @ModelAttribute InvoiceForm invoiceForm,
            RedirectAttributes redirectAttributes) {

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

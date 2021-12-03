package fhv.teamd.hotel.view;

import fhv.teamd.hotel.view.forms.StayForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("stay")
public class InvoiceController {
//
//    @GetMapping("performInvoice")
//    public RedirectView createInvoice(
//            @RequestParam String id,
//            RedirectAttributes redirectAttributes) {
//
//
//        return new RedirectView("/stay/createInvoice");
//    }

    @GetMapping("invoiceOverview")
    public ModelAndView createInvoice(
            @ModelAttribute StayForm stayForm,
            Model model) {

        model.addAttribute("stayForm", stayForm);

        return new ModelAndView("invoiceOverview");
    }

    @PostMapping("invoiceOverview")
    public RedirectView submitCreateInvoice(
            @ModelAttribute StayForm stayForm,
            Model model) {

        model.addAttribute("stayForm", stayForm);

        return new RedirectView("/stay/invoiceSummary");
    }

    @GetMapping("invoiceSummary")
    public ModelAndView invoiceSummary(
            @ModelAttribute StayForm stayForm,
            Model model) {

        model.addAttribute("stayForm", stayForm);

        return new ModelAndView("/stay/invoiceSummary");
    }

    @PostMapping("invoiceSummary")
    public RedirectView submitInvoiceSummary(
            @ModelAttribute StayForm stayForm,
            Model model) {

        // todo: success message
        model.addAttribute("stayForm", stayForm);

        return new RedirectView("/");
    }
}

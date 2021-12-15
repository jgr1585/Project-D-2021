package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BillingService;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
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
    public ModelAndView billList(@RequestParam(required = false) String stayId, Model model) {

        try {
            model.addAttribute("bill", this.billingService.intermediateBill(stayId));
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }

        model.addAttribute("stayId", stayId);

        return new ModelAndView("/invoice/billList");
    }

    @PostMapping("billList")
    public RedirectView submitBillList(@ModelAttribute String stayId, Model model,
                                       RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("stayId", stayId);

        return new RedirectView("billEdit");
    }

    @GetMapping("billEdit")
    public ModelAndView billEdit(@ModelAttribute String stayId,
                                 Model model) {

        model.addAttribute("stayId", stayId);

        return new ModelAndView("/invoice/billEdit");
    }

    @PostMapping("billEdit")
    public RedirectView submitBillEdit(@ModelAttribute String stayId, Model model,
                                       RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("stayId", stayId);

        return new RedirectView("billView");
    }

    @GetMapping("billView")
    public ModelAndView billView(@ModelAttribute String stayId, Model model) {

        model.addAttribute("stayId", stayId);

        return new ModelAndView("/invoice/billView");
    }

    @PostMapping("billView")
    public RedirectView submitBillView(@ModelAttribute String stayId, Model model,
                                       RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("stayId", stayId);

        if (stayId == null || stayId.equals("")) {
            return new RedirectView("/");
        } else {
            return new RedirectView("/invoice/billList?stayId=" + stayId);
        }
    }
}

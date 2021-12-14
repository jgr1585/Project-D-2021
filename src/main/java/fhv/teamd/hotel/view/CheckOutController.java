package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BillingService;
import fhv.teamd.hotel.application.FrontDeskService;
import fhv.teamd.hotel.application.dto.BillDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.domain.exceptions.AlreadyCheckedOutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("checkOut")
public class CheckOutController {

    @Autowired
    private FrontDeskService frontDeskService;

    @Autowired
    private BillingService billingService;

    @GetMapping("bill")
    public ModelAndView showBill(@RequestParam String stayId, Model model) {

        try {
            model.addAttribute("bill", this.billingService.intermediateBill(stayId));
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }

        model.addAttribute("stayId", stayId);

        return new ModelAndView("/checkOut/summary");
    }

    @RequestMapping("perform")
    public RedirectView checkOut(@RequestParam String stayId, Model model) {

        try {
            this.frontDeskService.checkOut(stayId);
        } catch (InvalidIdException | AlreadyCheckedOutException e) {
            e.printStackTrace();
        }

        return new RedirectView("/");
    }
}

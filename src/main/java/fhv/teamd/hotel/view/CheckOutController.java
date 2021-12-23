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
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("checkOut")
public class CheckOutController {

    @Autowired
    private FrontDeskService frontDeskService;

    @Autowired
    private BillingService billingService;

    @RequestMapping("summary")
    public RedirectView checkOutSummary(
            @RequestParam String stayId,
            @RequestParam int discountPercent,
            Model model) throws InvalidIdException {

        BillDTO billDTO = this.billingService.getBill(stayId);
        String reqParams = "?stayId=" + stayId + "&discountPercent=" + discountPercent;

        if (billDTO.entries().size() > 0) {
            return new RedirectView("/invoice/billList" + reqParams);
        } else {
            return new RedirectView("/checkOut/summary" + reqParams);
        }
    }

    @GetMapping("summary")
    public ModelAndView showBill(
            @RequestParam String stayId,
            @RequestParam int discountPercent,
            Model model) throws InvalidIdException {

        model.addAttribute("bill", this.billingService.getBill(stayId));
        model.addAttribute("stayId", stayId);
        model.addAttribute("discountPercent", (double) discountPercent / 100);

        return new ModelAndView("/checkOut/summary");
    }

    @RequestMapping("perform")
    public RedirectView checkOut(
            @RequestParam String stayId,
            Model model) throws AlreadyCheckedOutException, InvalidIdException {

        this.frontDeskService.checkOut(stayId);

        return new RedirectView("/");
    }
}

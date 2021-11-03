package fhv.teamd.hotel.view;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HotelViewController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/booking/chooseCategories")
    public ModelAndView createBooking() {
        return new ModelAndView("/booking/chooseCategories");
    }

    @GetMapping("/booking/personalDetails")
    public ModelAndView personalDetails() {
        return new ModelAndView("/booking/personalDetails");
    }

    @RequestMapping("/booking/submitCategories")
    public void submitCategories(HttpServletResponse response) throws IOException {
        // to do: read parameters and check for availability here

        // not available or other error: redirect back, with error msg
        //response.sendRedirect("/booking/chooseCategories");

        // success: redirect to next step
        response.sendRedirect("/booking/personalDetails");
    }
}

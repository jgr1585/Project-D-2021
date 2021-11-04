package fhv.teamd.hotel.view;

import fhv.teamd.hotel.view.forms.ChooseCategoriesForm;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HotelViewController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/booking/chooseCategories")
    public ModelAndView createBooking(
            @ModelAttribute ChooseCategoriesForm form,
            Model model) {

        Map<String, Integer> categories = new HashMap<>();
        categories.put("Single Bed TEST", 0);
        categories.put("Double Bed TEST", 0);
        form.setCategorySelection(categories);

        model.addAttribute("form", form);

        return new ModelAndView("/booking/chooseCategories");
    }

    @GetMapping("/booking/personalDetails")
    public ModelAndView personalDetails() {
        return new ModelAndView("/booking/personalDetails");
    }

    @PostMapping("/booking/submitCategories")
    public void submitCategories(
            @ModelAttribute ChooseCategoriesForm form,
            Model model,
            HttpServletResponse response) throws IOException {

        // to do: read parameters and check for availability here

        // not available or other error: redirect back, with error msg
        //response.sendRedirect("/booking/chooseCategories");

        // success: redirect to next step
        response.sendRedirect("/booking/personalDetails");
    }
}

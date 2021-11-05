package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.dto.BookableCategoryDTO;
import fhv.teamd.hotel.application.dto.BookingDTO;
import fhv.teamd.hotel.application.dto.DetailedBookingDTO;
import fhv.teamd.hotel.view.forms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.CustomerDetailsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HotelViewController {

    private static final Period defaultBookingLeadTime = Period.ofWeeks(2);
    private static final Period defaultStayDuration = Period.ofWeeks(1);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/")
    public ModelAndView index(Model model) {

        model.addAttribute("categories", this.categoryService.getAll());


        List<BookingDTO> bookings = this.bookingService.getAll();
        model.addAttribute("bookings", bookings);

        List<Optional<DetailedBookingDTO>> detailedBookings
                = bookings.stream()
                .map(bdto -> this.bookingService.getDetails(bdto.id()))
                .collect(Collectors.toUnmodifiableList());


        return new ModelAndView("index");
    }

    @GetMapping("/booking/bookingOverview")
    public ModelAndView bookingOverview(
            @ModelAttribute CustomerDetailsForm form,
            Model model) {

        model.addAttribute("form", form);

        return new ModelAndView("/booking/bookingOverview");
    }

    @GetMapping("/booking/chooseCategories")
    public ModelAndView createBooking(
            @ModelAttribute ChooseCategoriesForm form,
            Model model) {

        LocalDate defaultStartDate = LocalDate.now().plus(defaultBookingLeadTime);
        LocalDate defaultEndDate = defaultStartDate.plus(defaultStayDuration);

        List<BookableCategoryDTO> categories = this.categoryService.getAvailableCategories(
                defaultStartDate.atStartOfDay(),
                defaultEndDate.atStartOfDay());

        HashMap<String, ChooseCategoriesForm.CategoryField> categoriesWithValuesInFields = new HashMap<>();
        for(BookableCategoryDTO cat: categories) {
            categoriesWithValuesInFields.put(cat.categoryName(),
                    new ChooseCategoriesForm.CategoryField(cat, 0)); // 0 by default
        }

        form.setCategorySelection(categoriesWithValuesInFields);

        model.addAttribute("form", form);

        return new ModelAndView("/booking/chooseCategories");
    }

    @PostMapping("/booking/chooseCategories")
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

    @GetMapping("/booking/personalDetails")
    public ModelAndView personalDetails(
            @ModelAttribute CustomerDetailsForm form,
            Model model) {

        model.addAttribute("form", form);

        return new ModelAndView("/booking/personalDetails");
    }

    @PostMapping("/booking/personalDetails")
    public void submitCustomerDetails(
            @ModelAttribute CustomerDetailsForm form,
            Model model,
            HttpServletResponse response) throws IOException {

        // to do: read parameters and check for availability here

        // not available or other error: redirect back, with error msg
        //response.sendRedirect("/booking/chooseCategories");

        // success: redirect to next step
        response.sendRedirect("index");
    }
}

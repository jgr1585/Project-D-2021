package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.view.forms.BookingListForm;
import fhv.teamd.hotel.view.forms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.CustomerDetailsForm;
import fhv.teamd.hotel.view.forms.ExperimentalBookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
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
                .map(dbdto -> this.bookingService.getDetails(dbdto.id()))
                .collect(Collectors.toUnmodifiableList());


        return new ModelAndView("index");
    }

    @GetMapping("/booking/bookingOverview")
    public ModelAndView bookingOverview(
            @ModelAttribute BookingListForm form,
            Model model) {

        List<BookingDTO> bookings = this.bookingService.getAll();

        model.addAttribute("form", form);
        model.addAttribute("bookings", bookings);

        return new ModelAndView("/booking/bookingOverview");
    }

    @GetMapping("/booking/experimental")
    public ModelAndView experimentalForm(
            @ModelAttribute ExperimentalBookingForm form,
            Model model) {

        LocalDate defaultStartDate = LocalDate.now().plus(defaultBookingLeadTime);
        LocalDate defaultEndDate = defaultStartDate.plus(defaultStayDuration);

        form.setFromDate(defaultStartDate);
        form.setUntilDate(defaultEndDate);

        List<BookableCategoryDTO> categories = this.categoryService.getAvailableCategories(
                defaultStartDate.atStartOfDay(),
                defaultEndDate.atStartOfDay());

        // todo spaghetti
        HashMap<String, ExperimentalBookingForm.CategoryField> categoryMap = new HashMap<>();
        for(BookableCategoryDTO cat: categories) {
            categoryMap.put(cat.categoryId(),
                    new ExperimentalBookingForm.CategoryField(cat, 0)); // 0 by default
        }

        form.setCategorySelection(categoryMap);

        model.addAttribute("form", form);

        return new ModelAndView("/booking/experimentalBookingForm");
    }

    @PostMapping("/booking/experimental")
    public void experimentalSubmit(
            @ModelAttribute ExperimentalBookingForm form,
            Model model,
            HttpServletResponse response) throws IOException {

        HashMap<String, Integer> gottverdammt = new HashMap<>();

        for(Map.Entry<String, ExperimentalBookingForm.CategoryField> entry: form.getCategorySelection().entrySet()) {
            gottverdammt.put(entry.getKey(), entry.getValue().getSelectedAmount());
        }

        this.bookingService.book(
                gottverdammt,
                form.getFromDate().atStartOfDay(),
                form.getUntilDate().atStartOfDay(),
                GuestDetailsDTO.builder()
                        .withFirstName(form.getGuestFirstName())
                        .withLastName(form.getGuestLastName())
                        .withStreet(form.getGuestStreet())
                        .withZip(form.getGuestZip())
                        .withCity(form.getGuestCity())
                        .withCountry(form.getGuestCountry())
                        .build(),
                RepresentativeDetailsDTO.builder()
                        .withFirstName(form.getRepresentativeFirstName())
                        .withLastName(form.getRepresentativeLastName())
                        .withStreet(form.getRepresentativeStreet())
                        .withZip(form.getRepresentativeZip())
                        .withCity(form.getRepresentativeCity())
                        .withCountry(form.getRepresentativeCountry())
                        .withEmail(form.getRepresentativeMail())
                        .withPhone(form.getRepresentativePhone())
                        .build()
        );

        response.sendRedirect("/");


    }






    // old 2-part form






    @GetMapping("/booking/chooseCategories")
    public ModelAndView createBooking(
            @ModelAttribute ChooseCategoriesForm categoriesForm,
            Model model) {

        LocalDate defaultStartDate = LocalDate.now().plus(defaultBookingLeadTime);
        LocalDate defaultEndDate = defaultStartDate.plus(defaultStayDuration);

        List<BookableCategoryDTO> categories = this.categoryService.getAvailableCategories(
                defaultStartDate.atStartOfDay(),
                defaultEndDate.atStartOfDay());

        // todo spaghetti
        HashMap<String, ChooseCategoriesForm.CategoryField> defaultValues = new HashMap<>();
        for(BookableCategoryDTO cat: categories) {
            defaultValues.put(cat.categoryName(),
                    new ChooseCategoriesForm.CategoryField(cat, 0)); // 0 by default
        }

        categoriesForm.setCategorySelection(defaultValues);

        model.addAttribute("categoriesForm", categoriesForm);

        return new ModelAndView("/booking/chooseCategories");
    }

    @PostMapping("/booking/chooseCategories")
    public void submitCategories(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            Model model,
            HttpServletResponse response) throws IOException {

        model.addAttribute("chooseCategoriesForm", chooseCategoriesForm);

        // todo: check availability and other validations
        // todo: validation should be elsewhere

        LocalDate from = chooseCategoriesForm.getFrom();
        LocalDate until = chooseCategoriesForm.getUntil();

        boolean valid = from.isAfter(LocalDate.now()) && from.isBefore(until);



        if(!valid) {
            // todo add an error message obj to the model
            response.sendRedirect("/booking/chooseCategories");
        }

        // success: redirect to next step
        response.sendRedirect("/booking/personalDetails");
    }

    @GetMapping("/booking/personalDetails")
    public ModelAndView personalDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm, // keep first step of the 2-part form
            @ModelAttribute CustomerDetailsForm customerDetailsForm,
            Model model) {

        model.addAttribute("chooseCategoriesForm", chooseCategoriesForm);
        model.addAttribute("customerDetailsForm", customerDetailsForm);

        return new ModelAndView("/booking/personalDetails");
    }

    @PostMapping("/booking/personalDetails")
    public void submitCustomerDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute CustomerDetailsForm customerDetailsForm,
            Model model,
            HttpServletResponse response) throws IOException {

        // to do: read parameters and check for availability here

        // not available or other error: redirect back, with error msg
        //response.sendRedirect("/booking/chooseCategories");

        // success: redirect to next step
        response.sendRedirect("index");
    }
}

package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.view.forms.BookingListForm;
import fhv.teamd.hotel.view.forms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.PersonalDetailsForm;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        model.addAttribute("form", form);
        model.addAttribute("categories", categories);

        return new ModelAndView("/booking/experimentalBookingForm");
    }


    @GetMapping("/booking/chooseCategories")
    public ModelAndView createBooking(Model model) {

        LocalDate defaultStartDate = LocalDate.now().plus(defaultBookingLeadTime);
        LocalDate defaultEndDate = defaultStartDate.plus(defaultStayDuration);

        List<BookableCategoryDTO> categories = this.categoryService.getAvailableCategories(
                defaultStartDate.atStartOfDay(),
                defaultEndDate.atStartOfDay());

        Map<String, Integer> defaultValues
                = categories.stream().collect(Collectors.toMap(BookableCategoryDTO::categoryId, cat -> 0));

        ChooseCategoriesForm chooseCategoriesForm
                = new ChooseCategoriesForm(defaultStartDate, defaultEndDate, defaultValues);

        model.addAttribute("categories", categories);
        model.addAttribute("chooseCategoriesForm", chooseCategoriesForm);

        return new ModelAndView("/booking/chooseCategories");

    }

    @PostMapping("/booking/chooseCategories")
    public ModelAndView submitCategories(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            Model model,
            HttpServletResponse response) throws IOException {

        // todo: check availability and other validations
        // todo: validation should be elsewhere

        LocalDate from = chooseCategoriesForm.getFrom();
        LocalDate until = chooseCategoriesForm.getUntil();

        boolean valid = from.isAfter(LocalDate.now()) && from.isBefore(until);


        if (!valid) {
            // todo add an error message obj to the model
            response.sendRedirect("/booking/chooseCategories");
        }

        return this.personalDetails(chooseCategoriesForm, model);
    }

    @GetMapping("/booking/personalDetails")
    public ModelAndView personalDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm, // keep first step of the 2-part form
            Model model) {


        model.addAttribute("personalDetailsForm", new PersonalDetailsForm());


        return new ModelAndView("/booking/personalDetails");
    }


    @GetMapping("/booking/bookingSummary")
    public ModelAndView bookingSummary(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model,
            HttpServletResponse response) throws IOException {

        List<CategoryDTO> categories = new ArrayList<CategoryDTO>();

        for (String categoryId : chooseCategoriesForm.getCategorySelection().keySet()) {
            categories.add(this.categoryService.findCategoryById(categoryId).get());
        }

        model.addAttribute("categories", categories);

        return new ModelAndView("/booking/bookingSummary");
    }

    @PostMapping("/booking/submitPersonalDetails")
    public ModelAndView submitPersonalDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model,
            HttpServletResponse response) throws IOException {

        return this.bookingSummary(chooseCategoriesForm, personalDetailsForm, model, response);
    }

    @PostMapping("/booking/submit")
    public void submit(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model,
            HttpServletResponse response) throws IOException {

        GuestDetails guest = new GuestDetails(
                personalDetailsForm.getGuestFirstName(),
                personalDetailsForm.getGuestLastName(),
                new Address(
                        personalDetailsForm.getGuestStreet(),
                        personalDetailsForm.getGuestZip(),
                        personalDetailsForm.getGuestCity(),
                        personalDetailsForm.getGuestCountry()
                )
        );

        RepresentativeDetails rep = new RepresentativeDetails(
                personalDetailsForm.getRepresentativeFirstName(),
                personalDetailsForm.getRepresentativeLastName(),
                personalDetailsForm.getRepresentativeMail(),
                new Address(
                    personalDetailsForm.getRepresentativeStreet(),
                    personalDetailsForm.getRepresentativeZip(),
                    personalDetailsForm.getRepresentativeCity(),
                    personalDetailsForm.getRepresentativeCountry()),
                personalDetailsForm.getRepresentativePhone());

        this.bookingService.book(
                chooseCategoriesForm.getCategorySelection(),
                chooseCategoriesForm.getFrom().atStartOfDay(),
                chooseCategoriesForm.getUntil().atStartOfDay(),
                guest, rep
        );

        response.sendRedirect("/booking/bookingOverview");
    }

}

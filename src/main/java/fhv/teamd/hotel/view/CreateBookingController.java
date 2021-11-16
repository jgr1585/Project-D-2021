package fhv.teamd.hotel.view;


import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.view.forms.BookingForm;
import fhv.teamd.hotel.view.forms.subForms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.GlobalForm;
import fhv.teamd.hotel.view.forms.subForms.PersonalDetailsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("booking")
public class CreateBookingController {

    private static final Period defaultBookingLeadTime = Period.ofWeeks(2);
    private static final Period defaultStayDuration = Period.ofWeeks(1);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("chooseCategories")
    public ModelAndView chooseCategories(
            @ModelAttribute BookingForm bookingForm,
            Model model) {

        LocalDate defaultStartDate = LocalDate.now().plus(defaultBookingLeadTime);
        LocalDate defaultEndDate = defaultStartDate.plus(defaultStayDuration);

        List<AvailableCategoryDTO> categories = this.categoryService.getAvailableCategories(
                defaultStartDate.atStartOfDay(),
                defaultEndDate.atStartOfDay());

        Map<String, Integer> defaultValues
                = categories.stream().collect(Collectors.toMap(AvailableCategoryDTO::categoryId, cat -> 0));

        bookingForm.setChooseCategoriesForm(new ChooseCategoriesForm(defaultStartDate, defaultEndDate, defaultValues));

        model.addAttribute("categories", categories);
        model.addAttribute("bookingForm", bookingForm);

        return new ModelAndView("/booking/chooseCategories");
    }

    @PostMapping("chooseCategories")
    public RedirectView submitCategories(
            @ModelAttribute BookingForm bookingForm,
            Model model,
            RedirectAttributes redirectAttributes) {

        // todo: check availability with application service
        // todo: basic validation goes into form obj with annotations

        ChooseCategoriesForm chooseCategoriesForm = bookingForm.getChooseCategoriesForm();

        LocalDate from = chooseCategoriesForm.getFrom();
        LocalDate until = chooseCategoriesForm.getUntil();

        boolean valid = from.isAfter(LocalDate.now()) && from.isBefore(until);

        redirectAttributes.addFlashAttribute("bookingForm", bookingForm);

        if (!valid) {
            // add an error message here

            return new RedirectView("/booking/chooseCategories");
        }

        return new RedirectView("personalDetails");
    }

    @GetMapping("personalDetails")
    public ModelAndView personalDetails(
            @ModelAttribute BookingForm bookingForm,
            Model model) {

        bookingForm.setPersonalDetailsForm(new PersonalDetailsForm());
        model.addAttribute("bookingForm", bookingForm);

        return new ModelAndView("/booking/personalDetails");
    }

    @PostMapping("personalDetails")
    public RedirectView submitPersonalDetails(
            @RequestParam String action,
            @ModelAttribute BookingForm bookingForm,
            Model model,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("bookingForm", bookingForm);

        if (action.equals("prev")) {
            return new RedirectView("chooseCategories");
        }

        return new RedirectView("summary");
    }

    @GetMapping("summary")
    public ModelAndView bookingSummary(
            @ModelAttribute BookingForm bookingForm,
            Model model) {

        List<CategoryDTO> categories = new ArrayList<>();

        bookingForm.getChooseCategoriesForm().getCategorySelection().forEach((categoryId, amount) -> {
            if (amount > 0) {
                Optional<CategoryDTO> result = this.categoryService.findCategoryById(categoryId);
                result.ifPresent(categories::add);
            }
        });

        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("categories", categories);

        return new ModelAndView("/booking/bookingSummary");
    }

    @PostMapping("summary")
    public RedirectView submitBooking(
            @RequestParam String action,
            @ModelAttribute BookingForm bookingForm,

//            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
//            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model) {

        if (action.equals("prev")) {
            return new RedirectView("personalDetails");
        }

        PersonalDetailsForm personalDetailsForm = bookingForm.getPersonalDetailsForm();
        ChooseCategoriesForm chooseCategoriesForm = bookingForm.getChooseCategoriesForm();

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

        try {
            this.bookingService.book(
                    chooseCategoriesForm.getCategorySelection(),
                    chooseCategoriesForm.getFrom().atStartOfDay(),
                    chooseCategoriesForm.getUntil().atStartOfDay(),
                    guest, rep
            );
        } catch (Exception x) {
            x.printStackTrace();
            return new RedirectView("summary");
        }

        return new RedirectView("/booking/overview");
    }
}

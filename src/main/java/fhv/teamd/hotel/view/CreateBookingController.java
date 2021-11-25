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
import java.util.Optional;

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
            @RequestParam(required = false) String action,
            Model model) {

        ChooseCategoriesForm chooseCategoriesForm = bookingForm.getChooseCategoriesForm();

        if (!"prev".equals(action)) {
            LocalDate defaultStartDate = LocalDate.now().plus(defaultBookingLeadTime);
            LocalDate defaultEndDate = defaultStartDate.plus(defaultStayDuration);

            chooseCategoriesForm.setFrom(defaultStartDate);
            chooseCategoriesForm.setUntil(defaultEndDate);
        }

        List<AvailableCategoryDTO> categories
                = this.categoryService.getAvailableCategories(
                chooseCategoriesForm.getFrom().atStartOfDay(),
                chooseCategoriesForm.getUntil().atStartOfDay());

//        Map<String, Integer> defaultValues
//                = categories.stream().collect(Collectors.toMap(AvailableCategoryDTO::categoryId, cat -> 0));

        model.addAttribute("categories", categories);
        model.addAttribute("bookingForm", bookingForm);

        return new ModelAndView("/booking/chooseCategories");
    }

    @PostMapping("chooseCategories")
    public RedirectView submitCategories(
            @ModelAttribute BookingForm bookingForm,
            RedirectAttributes redirectAttributes) {

        // todo: check availability with application service
        // todo: basic validation goes into form obj with annotations

//        ChooseCategoriesForm chooseCategoriesForm = bookingForm.getChooseCategoriesForm();
//
//        LocalDate from = chooseCategoriesForm.getFrom();
//        LocalDate until = chooseCategoriesForm.getUntil();
//
//        boolean valid = from.isAfter(LocalDate.now()) && from.isBefore(until);
//
//        redirectAttributes.addFlashAttribute("bookingForm", bookingForm);
//
//        if (!valid) {
//            // todo: add an error message here
//            return new RedirectView("/booking/chooseCategories");
//        }

        redirectAttributes.addFlashAttribute("bookingForm", bookingForm);

        return new RedirectView("personalDetails");
    }

    @GetMapping("personalDetails")
    public ModelAndView personalDetails(
            @ModelAttribute BookingForm bookingForm,
            Model model) {

        model.addAttribute("bookingForm", bookingForm);

        return new ModelAndView("/booking/personalDetails");
    }

    @PostMapping("personalDetails")
    public RedirectView submitPersonalDetails(
            @ModelAttribute BookingForm bookingForm,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("bookingForm", bookingForm);

        if (action.equals("prev")) {
            redirectAttributes.addAttribute("action", "prev");
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

            if (amount != null && amount > 0) {

                Optional<CategoryDTO> result = this.categoryService.findCategoryById(categoryId);
                result.ifPresent(categories::add);

            }

        });

        model.addAttribute("categories", categories);
        model.addAttribute("bookingForm", bookingForm);

        return new ModelAndView("/booking/bookingSummary");
    }

    @PostMapping("summary")
    public RedirectView submitBooking(
            @ModelAttribute BookingForm bookingForm,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("bookingForm", bookingForm);

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

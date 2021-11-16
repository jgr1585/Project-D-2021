package fhv.teamd.hotel.view;


import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.view.forms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.PersonalDetailsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public ModelAndView chooseCategories(Model model) {

        LocalDate defaultStartDate = LocalDate.now().plus(defaultBookingLeadTime);
        LocalDate defaultEndDate = defaultStartDate.plus(defaultStayDuration);

        List<AvailableCategoryDTO> categories = this.categoryService.getAvailableCategories(
                defaultStartDate.atStartOfDay(),
                defaultEndDate.atStartOfDay());

        Map<String, Integer> defaultValues
                = categories.stream().collect(Collectors.toMap(AvailableCategoryDTO::categoryId, cat -> 0));

        ChooseCategoriesForm chooseCategoriesForm
                = new ChooseCategoriesForm(defaultStartDate, defaultEndDate, defaultValues);

        model.addAttribute("categories", categories);
        model.addAttribute("chooseCategoriesForm", chooseCategoriesForm);

        return new ModelAndView("/booking/chooseCategories");

    }

    @PostMapping("chooseCategories")
    public RedirectView submitCategories(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            Model model,
            RedirectAttributes redirectAttributes) {

        // todo: check availability with application service
        // todo: basic validation goes into form obj with annotations

        LocalDate from = chooseCategoriesForm.getFrom();
        LocalDate until = chooseCategoriesForm.getUntil();

        boolean valid = from.isAfter(LocalDate.now()) && from.isBefore(until);

        redirectAttributes.addFlashAttribute("chooseCategoriesForm", chooseCategoriesForm);


        if (!valid) {
            // add an error message here

            return new RedirectView("/booking/chooseCategories");
        }

        return new RedirectView("personalDetails");
    }

    @GetMapping("personalDetails")
    public ModelAndView personalDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm, // keep first step of the 2-part form
            Model model) {

        model.addAttribute("personalDetailsForm", new PersonalDetailsForm());

        return new ModelAndView("/booking/personalDetails");
    }

    @PostMapping("personalDetails")
    public RedirectView submitPersonalDetails(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("chooseCategoriesForm", chooseCategoriesForm);
        redirectAttributes.addFlashAttribute("personalDetailsForm", personalDetailsForm);

        return new RedirectView("summary");
    }

    @GetMapping("summary")
    public ModelAndView bookingSummary(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model) {

        List<CategoryDTO> categories = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : chooseCategoriesForm.getCategorySelection().entrySet()) {
            String categoryId = entry.getKey();
            Integer amount = entry.getValue();

            Optional<CategoryDTO> result = this.categoryService.findCategoryById(categoryId);

            if(amount > 0 && result.isPresent()) {
                categories.add(result.get());
            }
        }

        model.addAttribute("chooseCategoriesForm", chooseCategoriesForm);
        model.addAttribute("personalDetailsForm", personalDetailsForm);
        model.addAttribute("categories", categories);

        return new ModelAndView("/booking/bookingSummary");
    }

    @PostMapping("submit")
    public RedirectView submitBooking(
            @ModelAttribute ChooseCategoriesForm chooseCategoriesForm,
            @ModelAttribute PersonalDetailsForm personalDetailsForm,
            Model model) {

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
            return new RedirectView("/booking/summary");
        }

        return new RedirectView("/booking/overview");
    }
}

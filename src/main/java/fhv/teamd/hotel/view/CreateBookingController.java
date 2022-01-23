package fhv.teamd.hotel.view;


import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.OrganizationService;
import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.application.dto.OrganizationDTO;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.OrganizationDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.OrganizationId;
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
    private OrganizationService organizationService;

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


        model.addAttribute("categories", categories);
        model.addAttribute("bookingForm", bookingForm);

        return new ModelAndView("/booking/chooseCategories");
    }

    @PostMapping("chooseCategories")
    public RedirectView submitCategories(
            @ModelAttribute BookingForm bookingForm,
            RedirectAttributes redirectAttributes) {


        redirectAttributes.addFlashAttribute("bookingForm", bookingForm);

        return new RedirectView("personalDetails");
    }

    @GetMapping("personalDetails")
    public ModelAndView personalDetails(
            @ModelAttribute BookingForm bookingForm,
            Model model) {

        List<OrganizationDTO> organizations = this.organizationService.getAll();

        model.addAttribute("organizations", organizations);
        model.addAttribute("bookingForm", bookingForm);

        return new ModelAndView("/booking/personalDetails");
    }

    @PostMapping("personalDetails")
    public RedirectView submitPersonalDetails(
            @ModelAttribute BookingForm bookingForm,
            @RequestParam String action,
            @RequestParam(name = "isSameAsRep", required = false) boolean isSameAsRep,
            RedirectAttributes redirectAttributes) {

        bookingForm.getPersonalDetailsForm().setCheckBoxState(isSameAsRep);

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
                        personalDetailsForm.getRepresentativeCountry()
                ),
                personalDetailsForm.getRepresentativePhone(),
                personalDetailsForm.getRepresentativeCreditCardNumber(),
                personalDetailsForm.getRepresentativePaymentMethod()
        );

        try {
            String orgId = personalDetailsForm.getOrganizationDropDownId();

            if (orgId.equals("noOrganization")) {
                orgId = "";
            } else if (orgId.equals("addNewOrganization")) {
                OrganizationDetails organizationDetails = new OrganizationDetails(
                        personalDetailsForm.getOrganizationName(),
                        new Address(
                                personalDetailsForm.getOrganizationStreet(),
                                personalDetailsForm.getOrganizationZip(),
                                personalDetailsForm.getOrganizationCity(),
                                personalDetailsForm.getOrganizationCountry()
                        ),
                        personalDetailsForm.getDiscount()
                );

                orgId = this.organizationService.add(organizationDetails).toString();
            }

            this.bookingService.book(
                    chooseCategoriesForm.getCategorySelection(),
                    chooseCategoriesForm.getFrom().atStartOfDay(),
                    chooseCategoriesForm.getUntil().atStartOfDay(),
                    guest, rep, new OrganizationId(orgId)
            );

        } catch (Exception x) {
            x.printStackTrace();
            redirectAttributes.addFlashAttribute("error", x.getMessage());

            return new RedirectView("summary");
        }

        return new RedirectView("/booking/overview");
    }
}

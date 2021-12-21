package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.FrontDeskService;
import fhv.teamd.hotel.application.OrganizationService;
import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.view.forms.CheckInForm;
import fhv.teamd.hotel.view.forms.subForms.BookingListForm;
import fhv.teamd.hotel.view.forms.subForms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.subForms.PersonalDetailsForm;
import fhv.teamd.hotel.view.forms.subForms.RoomAssignmentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class HotelViewController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FrontDeskService frontDeskService;

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/")
    public ModelAndView index(Model model) {

        List<StayDTO> activeStays = this.frontDeskService.getActiveStays();
        List<OrganizationDTO> organizations = new ArrayList<>();

        for (StayDTO stay : activeStays) {
            Optional<OrganizationDTO> orgResult = this.organizationService.findOrganizationById(stay.organizationId());
            organizations.add(orgResult.orElse(OrganizationDTO.empty()));
        }

        model.addAttribute("stays", activeStays);
        model.addAttribute("organizations", organizations);

        return new ModelAndView("index");
    }

    @GetMapping("/booking/overview")
    public ModelAndView bookingOverview(
            @ModelAttribute BookingListForm form,
            Model model) {

        List<BookingDTO> bookings = this.bookingService.getActiveBookings();
        List<OrganizationDTO> organizations = new ArrayList<>();

        for (BookingDTO booking : bookings) {
            Optional<OrganizationDTO> orgResult = this.organizationService.findOrganizationById(booking.organizationId());
            organizations.add(orgResult.orElse(OrganizationDTO.empty()));
        }

        model.addAttribute("form", form);
        model.addAttribute("bookings", bookings);
        model.addAttribute("organizations", organizations);

        return new ModelAndView("/booking/bookingOverview");
    }

    @RequestMapping("/booking/performCheckIn")
    public RedirectView performCheckIn(
            @RequestParam String id,
            RedirectAttributes redirectAttributes) {

        Optional<DetailedBookingDTO> bookingDetailsResult = this.bookingService.getDetails(id);

        if (bookingDetailsResult.isEmpty()) {
            // should not happen normally
            return new RedirectView("/");
        }

        BookingDTO booking = bookingDetailsResult.get().basicInfo();
        Map<CategoryDTO, Integer> categories = bookingDetailsResult.get().details();

        Map<String, Integer> categoryIds = categories
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().id(),
                        Map.Entry::getValue
                ));

        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = checkIn.plus(Period.between(booking.fromDate(), booking.untilDate()));

        Optional<OrganizationDTO> orgResult = this.organizationService.findOrganizationById(booking.organizationId());
        OrganizationDTO organization = orgResult.orElse(OrganizationDTO.empty());

        redirectAttributes.addFlashAttribute("checkInForm", new CheckInForm(
                id,
                new ChooseCategoriesForm(checkIn, checkOut, categoryIds),
                new PersonalDetailsForm(
                        booking.organizationId(),

                        organization.organizationName(),
                        organization.address().street(),
                        organization.address().zip(),
                        organization.address().city(),
                        organization.address().country(),
                        organization.discount(),

                        booking.guest().firstName(),
                        booking.guest().lastName(),
                        booking.guest().address().street(),
                        booking.guest().address().zip(),
                        booking.guest().address().city(),
                        booking.guest().address().country(),

                        booking.representative().firstName(),
                        booking.representative().lastName(),
                        booking.representative().address().street(),
                        booking.representative().address().zip(),
                        booking.representative().address().city(),
                        booking.representative().address().country(),
                        booking.representative().email(),
                        booking.representative().phone(),
                        booking.representative().creditCardNumber(),
                        booking.representative().paymentMethod()
                ),
                new RoomAssignmentForm()
        ));

        return new RedirectView("/checkIn/chooseCategories");
    }
}
package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.FrontDeskService;
import fhv.teamd.hotel.application.OrganizationService;
import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.application.dto.contactInfo.AddressDTO;
import fhv.teamd.hotel.application.dto.contactInfo.GuestDetailsDTO;
import fhv.teamd.hotel.application.dto.contactInfo.RepresentativeDetailsDTO;
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
        List<OrganizationDTO> organizations = activeStays
                .stream()
                .map(stay -> this.organizationService
                        .findOrganizationById(stay.organizationId())
                        .orElse(OrganizationDTO.empty()))
                .collect(Collectors.toList());

        model.addAttribute("stays", activeStays);
        model.addAttribute("organizations", organizations);

        return new ModelAndView("index");
    }

    @GetMapping("/booking/overview")
    public ModelAndView bookingOverview(
            @ModelAttribute BookingListForm form,
            Model model) {

        List<BookingDTO> activeBookings = this.bookingService.getActiveBookings();
        List<OrganizationDTO> organizations = activeBookings
                .stream()
                .map(booking -> this.organizationService
                        .findOrganizationById(booking.organizationId())
                        .orElse(OrganizationDTO.empty()))
                .collect(Collectors.toList());

        model.addAttribute("form", form);
        model.addAttribute("bookings", activeBookings);
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

        GuestDetailsDTO guest = booking.guest();
        AddressDTO guestAddress = guest.address();

        RepresentativeDetailsDTO rep = booking.representative();
        AddressDTO repAddress = rep.address();

        boolean checkBoxState = guest.firstName().equals(rep.firstName())
                && guest.lastName().equals(rep.lastName())
                && guestAddress.equals(repAddress);

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

                        checkBoxState,

                        guest.firstName(),
                        guest.lastName(),
                        guestAddress.street(),
                        guestAddress.zip(),
                        guestAddress.city(),
                        guestAddress.country(),

                        rep.firstName(),
                        rep.lastName(),
                        repAddress.street(),
                        repAddress.zip(),
                        repAddress.city(),
                        repAddress.country(),
                        rep.email(),
                        rep.phone(),
                        rep.creditCardNumber(),
                        rep.paymentMethod()
                ),
                new RoomAssignmentForm()
        ));

        return new RedirectView("/checkIn/chooseCategories");
    }
}
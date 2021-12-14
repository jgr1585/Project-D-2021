package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BillingService;
import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.FrontDeskService;
import fhv.teamd.hotel.application.dto.BookingDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.application.dto.DetailedBookingDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.view.forms.CheckInForm;
import fhv.teamd.hotel.view.forms.subForms.BookingListForm;
import fhv.teamd.hotel.view.forms.subForms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.subForms.PersonalDetailsForm;
import fhv.teamd.hotel.view.forms.subForms.RoomAssignmentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HotelViewController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FrontDeskService frontDeskService;

    @GetMapping("/")
    public ModelAndView index(Model model) {

        model.addAttribute("stays", this.frontDeskService.getActiveStays());

        return new ModelAndView("index");
    }

    @GetMapping("/booking/overview")
    public ModelAndView bookingOverview(
            @ModelAttribute BookingListForm form,
            Model model) {

        List<BookingDTO> bookings = this.bookingService.getActiveBookings();

        model.addAttribute("form", form);
        model.addAttribute("bookings", bookings);

        return new ModelAndView("/booking/bookingOverview");
    }

    @RequestMapping("/booking/performCheckIn")
    public RedirectView performCheckIn(
            @RequestParam String id,
            RedirectAttributes redirectAttributes) {

        Optional<DetailedBookingDTO> result = this.bookingService.getDetails(id);

        if (result.isEmpty()) {
            // should not happen normally
            return new RedirectView("/");
        }

        BookingDTO booking = result.get().basicInfo();
        Map<CategoryDTO, Integer> categories = result.get().details();

        Map<String, Integer> categoryIds = categories.entrySet().stream().collect(
                Collectors.toMap(
                        entry -> entry.getKey().id(),
                        Map.Entry::getValue
                )
        );

        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = checkIn.plus(Period.between(booking.fromDate(), booking.untilDate()));

        redirectAttributes.addFlashAttribute("checkInForm", new CheckInForm(
                id,
                new ChooseCategoriesForm(checkIn, checkOut, categoryIds),
                new PersonalDetailsForm(
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

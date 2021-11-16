package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.*;
import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.domain.contactInfo.*;
import fhv.teamd.hotel.view.forms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HotelViewController {

    private static final Period defaultBookingLeadTime = Period.ofWeeks(2);
    private static final Period defaultStayDuration = Period.ofWeeks(1);


    @Autowired
    private BookingService bookingService;


    @Autowired
    private FrontDeskService frontDeskService;

    @GetMapping("/")
    public ModelAndView index(Model model) {

        model.addAttribute("stays", this.frontDeskService.getAllHotelStays());

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




}

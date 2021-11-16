package fhv.teamd.hotel.view;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.FrontDeskService;
import fhv.teamd.hotel.application.dto.BookingDTO;
import fhv.teamd.hotel.view.forms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Period;
import java.util.*;

@Controller
public class HotelViewController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FrontDeskService frontDeskService;

    @GetMapping("/")
    public ModelAndView index(Model model) {

        model.addAttribute("stays", this.frontDeskService.getAllHotelStays());

        return new ModelAndView("index");
    }

    @GetMapping("/booking/overview")
    public ModelAndView bookingOverview(
            @ModelAttribute BookingListForm form,
            Model model) {

        List<BookingDTO> bookings = this.bookingService.getAll();

        model.addAttribute("form", form);
        model.addAttribute("bookings", bookings);

        return new ModelAndView("/booking/bookingOverview");
    }




}

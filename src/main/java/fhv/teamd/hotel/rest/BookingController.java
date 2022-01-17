package fhv.teamd.hotel.rest;

import fhv.teamd.hotel.application.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rest/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

}

package fhv.teamd.hotel.rest;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;


@RestController
@RequestMapping("/rest/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    @ResponseBody
    public void book(@RequestParam Map<String, Integer> categoryIdsAndAmounts,
                     @RequestParam LocalDate from, @RequestParam LocalDate until,
                     @RequestParam GuestDetails guest, @RequestParam RepresentativeDetails representative) throws Exception {

        //TODO: Finish
        this.bookingService.book(categoryIdsAndAmounts, from.atStartOfDay(), until.atStartOfDay(), guest, representative, null);

    }


}

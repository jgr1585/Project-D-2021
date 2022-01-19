package fhv.teamd.hotel.rest;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.dto.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/rest/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    @ResponseBody
    public void book(@RequestParam Map<String, Integer> categoryIdsAndAmounts, @RequestParam BookingDTO bookingDTO) throws Exception {
        this.bookingService.book(categoryIdsAndAmounts, bookingDTO);
    }


}

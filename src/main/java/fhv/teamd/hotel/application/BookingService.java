package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.BookingDTO;
import fhv.teamd.hotel.application.dto.DetailedBookingDTO;
import fhv.teamd.hotel.application.dto.GuestDetailsDTO;
import fhv.teamd.hotel.application.dto.RequestedStayDTO;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    void book(RequestedStayDTO requestedStay, GuestDetailsDTO customerData);

    List<BookingDTO> getAll();

    Optional<DetailedBookingDTO> getDetails(String bookingId);

}

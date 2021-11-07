package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookingService {

    void book(Map<String, Integer> categoryIdsAndAmounts,
              LocalDateTime from, LocalDateTime until,
              GuestDetailsDTO guest, RepresentativeDetailsDTO representative);

    List<BookingDTO> getAll();

    Optional<DetailedBookingDTO> getDetails(String bookingId);

}

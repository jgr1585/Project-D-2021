package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookingService {

    void book(Map<String, Integer> categoryIdsAndAmounts,
              LocalDateTime from, LocalDateTime until,
              GuestDetails guest, RepresentativeDetails representative);

    List<BookingDTO> getAll();

    Optional<DetailedBookingDTO> getDetails(String bookingId);

}

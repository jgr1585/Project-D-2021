package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.OrganizationId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookingService {

    void book(Map<String, Integer> categoryIdsAndAmounts,
              LocalDateTime from, LocalDateTime until,
              GuestDetails guest, RepresentativeDetails representative, OrganizationId orgId) throws Exception;

    void book(Map<String, Integer> categoryIdsAndAmounts, BookingDTO bookingDTO) throws Exception;

    List<BookingDTO> getActiveBookings();

    Optional<DetailedBookingDTO> getDetails(String bookingId);
}

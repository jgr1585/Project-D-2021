package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Booking;

import java.awt.print.Book;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DetailedBookingDTO {

    private final BookingDTO booking;
    private final Map<CategoryDTO, Integer> details;

    public DetailedBookingDTO(BookingDTO basicInfo, Map<CategoryDTO, Integer> details) {
        this.booking = basicInfo;
        this.details = details;
    }

    public static DetailedBookingDTO fromBooking(Booking booking) {

        Map<CategoryDTO, Integer> details = booking.selection()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> CategoryDTO.fromCategory(entry.getKey()),
                        Map.Entry::getValue
                ));

        return new DetailedBookingDTO(BookingDTO.fromBooking(booking), details);
    }

    public BookingDTO basicInfo() {
        return this.booking;
    }

    public Map<CategoryDTO, Integer> details() {
        return Collections.unmodifiableMap(this.details);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DetailedBookingDTO that = (DetailedBookingDTO) o;
        return Objects.equals(this.booking, that.booking) && Objects.equals(this.details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.booking, this.details);
    }
}

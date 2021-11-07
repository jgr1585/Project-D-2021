package fhv.teamd.hotel.application.dto;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class DetailedBookingDTO {

    private final BookingDTO booking;
    private final Map<CategoryDTO, Integer> details;

    public DetailedBookingDTO(BookingDTO basicInfo, Map<CategoryDTO, Integer> details) {
        this.booking = basicInfo;
        this.details = details;
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

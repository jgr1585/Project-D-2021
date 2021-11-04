package fhv.teamd.hotel.application.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public final class BookingListDTO {

    private LocalDateTime fromDate;
    private LocalDateTime untilDate;
    private String representativeLastName;

    public static Builder builder() {
        return new Builder();
    }

    public LocalDateTime fromDate() {
        return this.fromDate;
    }

    public LocalDateTime untilDate() {
        return this.untilDate;
    }

    public String representativeLastName() {
        return this.representativeLastName;
    }

    private BookingListDTO() {
    }

    public static class Builder {
        private BookingListDTO instance;

        private Builder() {
            this.instance = new BookingListDTO();
        }

        public Builder withUntilDate(LocalDateTime untilDate) {
            this.instance.untilDate = untilDate;
            return this;
        }

        public Builder withFromDate(LocalDateTime fromDate) {
            this.instance.fromDate = fromDate;
            return this;
        }

        public Builder withRepresentativeLastName(String representativeLastName) {
            this.instance.representativeLastName = representativeLastName;
            return this;
        }

        public BookingListDTO build() {
            Objects.requireNonNull(this.instance.fromDate, "fromDate must be set in BookingListPrivatDTO");
            Objects.requireNonNull(this.instance.untilDate, "untilDate must be set in BookingListPrivatDTO");
            Objects.requireNonNull(this.instance.representativeLastName, "representativeLastName must be set in BookingListPrivatDTO");

            return this.instance;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final BookingListDTO that = (BookingListDTO) o;
        return Objects.equals(this.fromDate, that.fromDate) && Objects.equals(this.untilDate, that.untilDate) && Objects.equals(this.representativeLastName, that.representativeLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fromDate, this.untilDate, this.representativeLastName);
    }
}



package fhv.teamd.hotel.application.dto;
import fhv.teamd.hotel.domain.ids.BookingId;
import java.time.LocalDateTime;
import java.util.Objects;

public final class BookingListDTO {

    private LocalDateTime FromDate;
    private LocalDateTime UntilDate;
    private String BookerLastName;

    public static Builder builder() {
        return new Builder();
    }

    public LocalDateTime FromDate() {
        return FromDate;
    }

    public LocalDateTime UntilDate() {
        return UntilDate;
    }

    public String BookerLastName() {
        return BookerLastName;
    }

    private BookingListDTO() {

    }

    public static class Builder {
        private BookingListDTO instance;

        private Builder() {
            this.instance = new BookingListDTO();
        }

        public Builder withBookerLastName(String lastName) {
            this.instance.BookerLastName = lastName;
            return this;
        }

        public Builder withUntilDate(LocalDateTime untilDate) {
            this.instance.UntilDate = untilDate;
            return this;
        }

        public Builder withFromDate(LocalDateTime fromDate) {
            this.instance.FromDate = fromDate;
            return this;
        }

        public BookingListDTO build() {
            Objects.requireNonNull(this.instance.FromDate, "fromDate must be set in BookingListPrivatDTO");
            Objects.requireNonNull(this.instance.UntilDate, "untilDate must be set in BookingListPrivatDTO");
            Objects.requireNonNull(this.instance.BookerLastName, "BookerLastName must be set in BookingListPrivatDTO");
            return this.instance;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(FromDate, UntilDate, BookerLastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookingListDTO that = (BookingListDTO) o;
        return Objects.equals(FromDate, that.FromDate) && Objects.equals(UntilDate, that.UntilDate) && Objects.equals(BookerLastName, that.BookerLastName);
    }


}



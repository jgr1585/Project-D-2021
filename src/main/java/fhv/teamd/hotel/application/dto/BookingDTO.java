package fhv.teamd.hotel.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public final class BookingDTO {

    private String id;
    private LocalDateTime fromDate;
    private LocalDateTime untilDate;
    private String representativeName;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return this.id;
    }

    public LocalDate fromDate() {
        return this.fromDate.toLocalDate();
    }

    public LocalDate untilDate() {
        return this.untilDate.toLocalDate();
    }


    public String representativeName() {
        return this.representativeName;
    }

    private BookingDTO() {
    }

    public static class Builder {
        private BookingDTO instance;

        private Builder() {
            this.instance = new BookingDTO();
        }

        public Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public Builder withUntilDate(LocalDateTime untilDate) {
            this.instance.untilDate = untilDate;
            return this;
        }

        public Builder withFromDate(LocalDateTime fromDate) {
            this.instance.fromDate = fromDate;
            return this;
        }

        public Builder withRepresentativeName(String representativeName) {
            this.instance.representativeName = representativeName;
            return this;
        }

        public BookingDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in BookingListPrivatDTO");
            Objects.requireNonNull(this.instance.fromDate, "fromDate must be set in BookingListPrivatDTO");
            Objects.requireNonNull(this.instance.untilDate, "untilDate must be set in BookingListPrivatDTO");
            Objects.requireNonNull(this.instance.representativeName, "representativeLastName must be set in BookingListPrivatDTO");

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
        final BookingDTO that = (BookingDTO) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.fromDate, that.fromDate) && Objects.equals(this.untilDate, that.untilDate) && Objects.equals(this.representativeName, that.representativeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.fromDate, this.untilDate, this.representativeName);
    }
}



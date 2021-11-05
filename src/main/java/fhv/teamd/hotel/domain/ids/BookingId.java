package fhv.teamd.hotel.domain.ids;

import java.util.Objects;

public class BookingId {

    private String id;

    private BookingId() { }

    public BookingId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final BookingId that = (BookingId) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return this.id;
    }
}

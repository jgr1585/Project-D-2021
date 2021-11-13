package fhv.teamd.hotel.domain.ids;

import java.util.Objects;

public class StayId {
    private final String id;

    public StayId(String id) {
        this.id = id;
    }

    private StayId() {
        // hibernate
        this(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final StayId stayId = (StayId) o;
        return Objects.equals(this.id, stayId.id);
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

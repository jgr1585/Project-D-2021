package fhv.teamd.hotel.domain.ids;

import java.util.Objects;

public class RoomId {

    private final String id;

    public RoomId(String id) {
        this.id = id;
    }

    private RoomId() {
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
        final RoomId roomId = (RoomId) o;
        return Objects.equals(this.id, roomId.id);
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

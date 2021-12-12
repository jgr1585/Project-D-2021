package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.RoomId;

import java.util.Objects;

public class Room {

    private Long id;

    private RoomId domainId;

    private Category category;


    private Room() {
        // hibernate
    }

    //Only for Test
    @Deprecated
    public Room(Long id, RoomId roomId, Category category) {
        this.id = id;
        this.domainId = roomId;
        this.category = category;
    }

    protected Long id() {
        return this.id;
    }


    public RoomId roomId() {
        return this.domainId;
    }

    public Category category() {
        return this.category;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Room room = (Room) o;
        return Objects.equals(this.id, room.id) && Objects.equals(this.domainId, room.domainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.domainId);
    }

    @Override
    public String toString() {
        return this.domainId.toString();
    }
}

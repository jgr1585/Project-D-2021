package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.RoomId;

import java.util.Objects;

public class Room {

    private Long id;

    private RoomId roomId;

    private Category category;


    private Room() {
        // hibernate
    }

    //Only for Test
    @Deprecated
    public Room(Long id, RoomId roomId, Category category) {
        this.id = id;
        this.roomId = roomId;
        this.category = category;
    }

    protected Long id() {
        return this.id;
    }


    public RoomId roomId() {
        return this.roomId;
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
        return Objects.equals(this.id, room.id) && Objects.equals(this.roomId, room.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.roomId);
    }

    @Override
    public String toString() {
        return this.roomId.toString();
    }
}

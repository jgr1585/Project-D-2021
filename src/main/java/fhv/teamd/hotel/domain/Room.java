package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.RoomId;

public class Room {

    private Long id;

    private RoomId roomId;

    private Category category;


    public Room() {
        // hibernate
    }


    public RoomId id() {
        return this.roomId;
    }

    public Category category() {
        return this.category;
    }
}

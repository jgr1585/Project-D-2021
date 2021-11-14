package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.RoomId;

public class Room {

    private Long id;

    private RoomId roomId;

    private Category category;


    private Room() {
        // hibernate
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
}

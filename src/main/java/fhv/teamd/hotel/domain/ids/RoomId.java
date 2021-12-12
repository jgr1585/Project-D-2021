package fhv.teamd.hotel.domain.ids;

import fhv.teamd.hotel.domain.Room;

public class RoomId extends DomainId<Room> {
    private RoomId() {
    }

    public RoomId(String id) {
        super(id);
    }
}

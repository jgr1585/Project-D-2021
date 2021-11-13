package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.StayId;

import java.util.List;

public class Stay {

    private Long id;

    private StayId stayId;

    List<Room> rooms;

    private Stay() {
        // hibernate
    }

}

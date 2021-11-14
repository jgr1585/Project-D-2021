package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.ids.StayId;

import java.util.List;

public interface StayRepository {

    StayId nextIdentity();

    List<Stay> getAll();

    void put(Stay stay);

}

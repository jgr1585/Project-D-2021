package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Stay;

import java.util.List;

public interface StayRepository {

    List<Stay> getAll();

    void put(Stay stay);

}

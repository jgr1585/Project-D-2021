package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.ids.StayId;

import java.time.LocalDateTime;
import java.util.List;

public interface StayRepository {

    StayId nextIdentity();

    List<Stay> getAll();

    List<Stay> staysInTimeFrameInclusive(LocalDateTime from, LocalDateTime until);

    void put(Stay stay);

}

package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.StayId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StayRepository {

    StayId nextIdentity();

    List<Stay> getAll();

    List<Stay> getActiveStays();

    List<Stay> activeStaysWithOverlappingDuration(LocalDateTime from, LocalDateTime until);

    int numberOfStayRoomsByCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until);

    void put(Stay stay);

    Optional<Stay> findById(StayId stayId);
}

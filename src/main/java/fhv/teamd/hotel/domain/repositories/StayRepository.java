package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.StayId;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.List;

public interface StayRepository {

    StayId nextIdentity();

    List<Stay> getAll();

    List<Stay> staysWithOverlappingDuration(LocalDateTime from, LocalDateTime until);

    int getNumberOfStayRoomsByCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until);

    void put(Stay stay);

}

package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.RoomId;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    List<Room> getAll();

    List<Room> getByCategory(CategoryId categoryId);

    Optional<Room> getById(RoomId roomId);

}

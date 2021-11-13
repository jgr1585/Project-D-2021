package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;

import java.util.List;

public interface RoomRepository {

    List<Room> getAll();

    List<Room> getByCategory(CategoryId categoryId);

}

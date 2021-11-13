package fhv.teamd.hotel.view.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomAssignmentForm {

    // maps a categoryId to a list of roomIds
    // = list of assigned rooms for each booked category.

    private final Map<String, List<String>> categoriesAndRooms;

    public RoomAssignmentForm() {
        this.categoriesAndRooms = new HashMap<>();
    }

    // mutable getter!
    public Map<String, List<String>> getCategoriesAndRooms() {
        return this.categoriesAndRooms;
    }
}

package fhv.teamd.hotel.view.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomAssignmentForm {

    // maps a categoryId to a list of roomIds
    // = list of assigned rooms for each booked category.

    private Map<String, List<String>> categoriesAndRooms;
    private Map<String, String> mapping;

    public RoomAssignmentForm() {
        this.categoriesAndRooms = new HashMap<>();
        this.mapping = new HashMap<>();
    }

    public Map<String, List<String>> getCategoriesAndRooms() {
        return this.categoriesAndRooms;
    }

    public void setCategoriesAndRooms(Map<String, List<String>> categoriesAndRooms) {
        this.categoriesAndRooms = categoriesAndRooms;
    }

    public Map<String, String> getMapping() {
        return this.mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }
}

package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Room;

import java.util.Objects;

public class RoomDTO {

    private final String id;
    private final String categoryId;

    public RoomDTO(String id, String categoryId) {
        this.id = id;
        this.categoryId = categoryId;
    }

    public static RoomDTO fromRoom(Room room) {
        return new RoomDTO(
                room.roomId().toString(),
                room.category().categoryId().toString());
    }

    public String id() {
        return this.id;
    }

    public String categoryId() {
        return this.categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final RoomDTO roomDTO = (RoomDTO) o;
        return Objects.equals(this.id, roomDTO.id) && Objects.equals(this.categoryId, roomDTO.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.categoryId);
    }
}

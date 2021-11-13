package fhv.teamd.hotel.application.dto;

import java.util.Objects;

public class RoomDTO {

    private String id;
    private String categoryId;

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

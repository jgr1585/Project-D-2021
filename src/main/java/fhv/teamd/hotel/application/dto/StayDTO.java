package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StayDTO {

    private final String id;

    @DateTimeFormat
    private final LocalDateTime checkIn;
    @DateTimeFormat
    private final LocalDateTime expectedCheckOut;

    private final RepresentativeDetails representative;
    private final GuestDetails guest;

    private final List<RoomDTO> rooms;

    public StayDTO(String id, LocalDateTime checkIn, LocalDateTime expectedCheckOut, RepresentativeDetails representative, GuestDetails guest, List<RoomDTO> rooms) {
        this.id = id;
        this.checkIn = checkIn;
        this.expectedCheckOut = expectedCheckOut;
        this.representative = representative;
        this.guest = guest;
        this.rooms = rooms;
    }

    public static StayDTO fromStay(Stay stay) {
        return new StayDTO(
                stay.stayId().toString(),
                stay.checkIn(),
                stay.expectedCheckOut(),
                stay.representativeDetails(),
                stay.guestDetails(),
                stay.rooms().stream().map(RoomDTO::fromRoom).collect(Collectors.toList())
        );
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime checkIn() {
        return this.checkIn;
    }

    public LocalDateTime expectedCheckOut() {
        return this.expectedCheckOut;
    }

    public RepresentativeDetails representative() {
        return this.representative;
    }

    public GuestDetails guest() {
        return this.guest;
    }

    public List<RoomDTO> rooms() {
        return Collections.unmodifiableList(this.rooms);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final StayDTO stayDTO = (StayDTO) o;
        return Objects.equals(this.id, stayDTO.id) && Objects.equals(this.checkIn, stayDTO.checkIn) && Objects.equals(this.expectedCheckOut, stayDTO.expectedCheckOut) && Objects.equals(this.representative, stayDTO.representative) && Objects.equals(this.guest, stayDTO.guest) && Objects.equals(this.rooms, stayDTO.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.checkIn, this.expectedCheckOut, this.representative, this.guest, this.rooms);
    }
}

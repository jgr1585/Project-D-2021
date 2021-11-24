package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.StayId;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Stay {

    private Long id;
    private StayId stayId;

    private LocalDateTime checkIn;
    private LocalDateTime expectedCheckOut;

    private Set<Room> rooms;

    private GuestDetails guestDetails;
    private RepresentativeDetails representativeDetails;

    private StayingState stayingState;

    protected Stay() {
        // hibernate
    }

    public Stay(StayId stayId, LocalDateTime checkIn, LocalDateTime expectedCheckOut,
                Set<Room> rooms,
                GuestDetails guest, RepresentativeDetails representative, StayingState stayingState) {

        this.stayId = stayId;

        if(checkIn.isAfter(expectedCheckOut)) {
            throw new InvalidParameterException("check in must be before check out");
        }

        this.checkIn = checkIn;
        this.expectedCheckOut = expectedCheckOut;

        if(rooms.size() == 0) {
            throw new InvalidParameterException("no rooms");
        }

        this.rooms = rooms;

        this.guestDetails = guest;
        this.representativeDetails = representative;

        this.stayingState = stayingState;
    }

    protected Long id() {
        return this.id;
    }

    public StayId stayId() {
        return this.stayId;
    }

    public LocalDateTime checkIn() {
        return this.checkIn;
    }

    public LocalDateTime checkOut() {
        return this.expectedCheckOut;
    }

    public Set<Room> rooms() {
        return Collections.unmodifiableSet(this.rooms);
    }

    public GuestDetails guestDetails() {
        return this.guestDetails;
    }

    public RepresentativeDetails representativeDetails() {
        return this.representativeDetails;
    }

    public StayingState getStayingState() {
        return this.stayingState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Stay stay = (Stay) o;
        return Objects.equals(this.id, stay.id) && Objects.equals(this.stayId, stay.stayId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.stayId);
    }
}

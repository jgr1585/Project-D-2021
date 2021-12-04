package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.exceptions.AlreadyCheckedOutException;
import fhv.teamd.hotel.domain.ids.StayId;

import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalUnit;
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

    private Bill intermediateBill;

    protected Stay() {
        // hibernate
    }

    public static Stay create(StayId stayId, LocalDateTime checkIn, LocalDateTime expectedCheckOut,
                Set<Room> rooms,
                GuestDetails guest, RepresentativeDetails representative) {

        Stay stay = new Stay();

        stay.stayId = stayId;

        if(checkIn.isAfter(expectedCheckOut)) {
            throw new InvalidParameterException("check in must be before check out");
        }

        stay.checkIn = checkIn;
        stay.expectedCheckOut = expectedCheckOut;

        if(rooms.size() == 0) {
            throw new InvalidParameterException("no rooms");
        }

        stay.rooms = rooms;

        stay.guestDetails = guest;
        stay.representativeDetails = representative;

        stay.stayingState = StayingState.CheckedIn;

        stay.intermediateBill = Bill.createEmpty();

        return stay;
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

    public LocalDateTime expectedCheckOut() {
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

    public Bill intermediateBill() {
        return this.intermediateBill;
    }

    public boolean isActive() {
        return this.stayingState.equals(StayingState.CheckedIn);
    }


    public void checkOut() throws AlreadyCheckedOutException {
        if (this.stayingState.equals(StayingState.CheckedOut)){
            throw new AlreadyCheckedOutException();
        }
        this.stayingState = StayingState.CheckedOut;


        int nights = (int)Duration.between(this.checkIn, LocalDateTime.now()).toDays();

        for (Room room: this.rooms) {

            Category category = room.category();
            this.intermediateBill.charge(nights + " in " + room, nights, category.pricePerNight());
        }

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

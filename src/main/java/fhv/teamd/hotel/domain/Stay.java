package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.exceptions.AlreadyCheckedOutException;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.domain.ids.StayId;

import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Stay {

    private Long id;
    private StayId domainId;

    private LocalDateTime checkIn;
    private LocalDateTime expectedCheckOut;

    private Set<Room> rooms;

    private GuestDetails guestDetails;
    private RepresentativeDetails representativeDetails;

    private OrganizationId organizationId;
    private StayingState stayingState;
    private Bill bill;

    protected Stay() {
        // hibernate
    }

    //Test only
    @Deprecated
    public Stay(Long id, StayId domainId, LocalDateTime checkIn, LocalDateTime expectedCheckOut,
                Set<Room> rooms,
                RepresentativeDetails representativeDetails, GuestDetails guestDetails, StayingState stayingState, OrganizationId organizationId) {
        this.id = id;
        this.domainId = domainId;
        this.checkIn = checkIn;
        this.expectedCheckOut = expectedCheckOut;
        this.rooms = rooms;
        this.guestDetails = guestDetails;
        this.representativeDetails = representativeDetails;
        this.stayingState = stayingState;
        this.organizationId = organizationId;
        this.bill = Bill.createEmpty();
    }


    public static Stay create(StayId stayId, LocalDateTime checkIn, LocalDateTime expectedCheckOut,
                              Set<Room> rooms,
                              GuestDetails guest, RepresentativeDetails representative, Season season, OrganizationId organizationId) {

        Stay stay = new Stay();

        stay.domainId = stayId;

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
        stay.organizationId = organizationId;
        stay.bill = Bill.createEmpty();

        int nights = (int)Duration.between(checkIn, LocalDateTime.now()).toDays();
        for (Room room: rooms) {
            Category category = room.category();
            stay.bill.charge("Night(s) in " + room, nights, category.pricePerNight(season));
        }

        return stay;
    }

    protected Long id() {
        return this.id;
    }

    public StayId stayId() {
        return this.domainId;
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

    public boolean isActive() {
        return this.stayingState.equals(StayingState.CheckedIn);
    }

    public OrganizationId organizationId() {
        return this.organizationId;
    }

    public void checkOut() throws AlreadyCheckedOutException {
        if (this.stayingState.equals(StayingState.CheckedOut)){
            throw new AlreadyCheckedOutException();
        }
        this.stayingState = StayingState.CheckedOut;
    }

    public Bill bill() {

        return this.bill;
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
        return Objects.equals(this.id, stay.id) && Objects.equals(this.domainId, stay.domainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.domainId);
    }


}

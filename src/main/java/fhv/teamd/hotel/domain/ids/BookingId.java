package fhv.teamd.hotel.domain.ids;

import fhv.teamd.hotel.domain.Booking;

public class BookingId extends DomainId<Booking> {
    private BookingId() {
    }

    public BookingId(String id) {
        super(id);
    }
}

package fhv.teamd.hotel.application;


import fhv.teamd.hotel.application.dto.StayDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.exceptions.AlreadyCheckedOutException;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface FrontDeskService {

    void checkInWalkInGuest(List<String> roomIds, Duration expectedDuration,
                 GuestDetails guest, RepresentativeDetails representative) throws InvalidIdException;

    void checkInWithBooking(List<String> roomIds, Duration expectedDuration,
                            GuestDetails guest, RepresentativeDetails representative,
                            String bookingId) throws InvalidIdException;

    List<StayDTO> getAllHotelStays();

    void checkOut(String stayID) throws InvalidIdException, AlreadyCheckedOutException;

}

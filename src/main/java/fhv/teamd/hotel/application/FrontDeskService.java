package fhv.teamd.hotel.application;


import fhv.teamd.hotel.application.dto.StayDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.application.exceptions.OccupiedRoomException;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface FrontDeskService {

    void checkInWalkInGuest(List<String> roomIds, Duration expectedDuration,
                 GuestDetails guest, RepresentativeDetails representative) throws InvalidIdException, OccupiedRoomException;

    void checkInWithBooking(List<String> roomIds, Duration expectedDuration,
                            GuestDetails guest, RepresentativeDetails representative,
                            String bookingId) throws InvalidIdException, OccupiedRoomException;

    List<StayDTO> getAllHotelStays();

}

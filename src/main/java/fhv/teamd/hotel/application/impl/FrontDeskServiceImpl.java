package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.FrontDeskService;
import fhv.teamd.hotel.application.dto.BillDTO;
import fhv.teamd.hotel.application.dto.StayDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.application.exceptions.OccupiedRoomException;
import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.exceptions.AlreadyCheckedOutException;
import fhv.teamd.hotel.domain.exceptions.CannotCheckinException;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.RoomId;
import fhv.teamd.hotel.domain.ids.StayId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import fhv.teamd.hotel.domain.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FrontDeskServiceImpl implements FrontDeskService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private AvailabilityService availabilityService;

    @Transactional
    @Override
    public void checkInWalkInGuest(List<String> roomIds, Duration expectedDuration,
                                   GuestDetails guest, RepresentativeDetails representative) throws InvalidIdException, OccupiedRoomException {

        Set<Room> rooms = new HashSet<>();

        for (String roomId : roomIds) {

            Optional<Room> result = this.roomRepository.findById(new RoomId(roomId));

            if (result.isEmpty()) {
                throw new InvalidIdException("room id");
            }
            rooms.add(result.get());
        }

        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plus(expectedDuration);

        if (!this.availabilityService.areAvailable(rooms, checkIn, checkOut)) {
            throw new OccupiedRoomException("occupied room");
        }

        this.stayRepository.put(Stay.create(
                this.stayRepository.nextIdentity(),
                checkIn, checkOut, rooms,
                guest, representative
        ));
    }

    @Transactional
    @Override
    public void checkInWithBooking(List<String> roomIds, Duration expectedDuration,
                                   GuestDetails guest, RepresentativeDetails representative,
                                   String bookingId) throws InvalidIdException, OccupiedRoomException, CannotCheckinException {

        this.checkInWalkInGuest(roomIds, expectedDuration, guest, representative);

        Optional<Booking> result = this.bookingRepository.findById(new BookingId(bookingId));

        Booking booking = result.orElseThrow(() -> new InvalidIdException(bookingId));

        booking.notifyOfCheckin();
    }

    @Override
    @Transactional
    public List<StayDTO> getActiveStays() {
        return this.stayRepository.getActiveStays()
                .stream()
                .map(StayDTO::fromStay)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public BillDTO intermediateBill(String stayId) throws InvalidIdException {

        return BillDTO.fromBill(
                this.stayRepository.findById(new StayId(stayId))
                .orElseThrow(() -> new InvalidIdException("stay id"))
                .generateIntermediateBill());

    }

    @Override
    @Transactional
    public BillDTO checkOut(String stayID) throws InvalidIdException, AlreadyCheckedOutException {
        Optional<Stay> result = this.stayRepository.findById(new StayId(stayID));

        Stay stay = result.orElseThrow(() -> new InvalidIdException("stay id"));

        stay.checkOut();

        return BillDTO.fromBill(stay.generateIntermediateBill());

    }
}

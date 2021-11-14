package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.FrontDeskService;
import fhv.teamd.hotel.application.dto.StayDTO;
import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.RoomId;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FrontDeskServiceImpl implements FrontDeskService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StayRepository stayRepository;

    @Transactional
    @Override
    public void checkIn(List<String> roomIds, Duration expectedDuration,
                        GuestDetails guest, RepresentativeDetails representative) throws Exception {

        Set<Room> rooms = new HashSet<>();

        for(String roomId: roomIds) {

            Optional<Room> result = this.roomRepository.getById(new RoomId(roomId));

            if(result.isEmpty()) {
                throw new Exception("invalid room id");
            }

            rooms.add(result.get());
        }


        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plus(expectedDuration);


        Stay newStay = new Stay(this.stayRepository.nextIdentity(), checkIn, checkOut, rooms, guest, representative);

        this.stayRepository.put(newStay);
    }

    @Override
    @Transactional
    public List<StayDTO> getAllHotelStays() {
        return this.stayRepository.getAll()
                .stream()
                .map(StayDTO::fromStay)
                .collect(Collectors.toList());
    }
}

package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.StayDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.application.exceptions.OccupiedRoomException;
import fhv.teamd.hotel.domain.*;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.domain.ids.RoomId;
import fhv.teamd.hotel.domain.ids.StayId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import fhv.teamd.hotel.domain.services.AvailabilityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class FrontDeskServiceTests {

    @Autowired
    private FrontDeskService frontDeskService;

    @MockBean
    private StayRepository stayRepository;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private AvailabilityService availabilityService;

    @MockBean
    private BookingRepository bookingRepository;

    @Captor
    private ArgumentCaptor<Stay> actualStay;

    @BeforeEach
    public void initMock() {

        final LocalDateTime yesterday = LocalDateTime.now().minus(Period.ofDays(1));
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime tomorrow = LocalDateTime.now().plus(Period.ofDays(1));

        @SuppressWarnings("SpellCheckingInspection")
        final Address addr = new Address("musterstrasse 1", "1234", "musterort", "musterland");

        final GuestDetails guest = new GuestDetails( "max", "muster", addr);

        final RepresentativeDetails rep = new RepresentativeDetails(
                "max","muster","m@mail.com", addr,"123456",
                "1111 1111 1111 1111", PaymentMethod.CreditCard);

        final Category cat1 = DomainFactory.createCategory();
        final Category cat2 = DomainFactory.createCategory();

        //noinspection deprecation
        final Room room1 = new Room(1L, new RoomId("Room 1"), cat1);
        final Set<Room> rooms1 = new HashSet<>();
        rooms1.add(room1);

        //noinspection deprecation
        final Room room2 = new Room(2L, new RoomId("Room 2"), cat2);
        final Set<Room> rooms2 = new HashSet<>();
        rooms2.add(room2);

        Stay stay1 = Stay.create(new StayId("stay-1"), yesterday, tomorrow, rooms1, guest, rep, DomainFactory.createSeason(), new OrganizationId(""));
        Stay stay2 = Stay.create(new StayId("stay-2"), yesterday, now, rooms2, guest, rep, DomainFactory.createSeason(), new OrganizationId(""));

        ReflectionTestUtils.setField(stay2, "stayingState", StayingState.CheckedOut);

        List<Stay> stays = new ArrayList<>(List.of(stay1, stay2));

        Mockito.when(this.stayRepository.getAll()).thenReturn(stays);
        Mockito.when(this.stayRepository.getActiveStays()).thenReturn(List.of(stay1));
        Mockito.when(this.stayRepository.findById(new StayId("stay-1"))).thenReturn(Optional.of(stay1));
        Mockito.when(this.stayRepository.findById(new StayId("stay-2"))).thenReturn(Optional.of(stay2));

    }

    @Test
    void given_emptyRepository_when_getAll_returnsEmpty() {
        Mockito.when(this.stayRepository.getActiveStays()).thenReturn(Collections.emptyList());
        Assertions.assertNotEquals(null, this.frontDeskService.getActiveStays());
        Assertions.assertEquals(0, this.frontDeskService.getActiveStays().size());
    }

    @Test
    void given_emptyRepository_when_getAll_returnsAll() {

        final List<StayDTO> actual = this.frontDeskService.getActiveStays();

        final List<StayDTO> expected = this.stayRepository.getActiveStays()
                .stream()
                .map(StayDTO::fromStay)
                .collect(Collectors.toList());

        Assertions.assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }



    @Test
    void given_WalkInGuest_when_CheckIn_then_CreateStay() {
        final RepresentativeDetails rep = DomainFactory.createRepresentativeDetails();
        final GuestDetails guestDetails = DomainFactory.getFromRepresentativeDetails(rep);
        final Room room = DomainFactory.createRoom();
        final List<String> rooms = new LinkedList<>();
        final LocalDateTime checkOutDate = LocalDateTime.now().plus(Period.ofDays(1));
        rooms.add(room.roomId().toString());
        final StayId stayId = DomainFactory.createStayId();
        final Set<Room> roomsSet = new HashSet<>();
        roomsSet.add(room);
        final Stay expected = Stay.create(stayId, LocalDateTime.now(), checkOutDate, roomsSet, guestDetails, rep, DomainFactory.createSeason(), new OrganizationId(""));
        final OrganizationId organizationId = new OrganizationId("");

        Mockito.when(this.stayRepository.nextIdentity()).thenReturn(stayId);
        Mockito.when(this.roomRepository.findById(room.roomId())).thenReturn(Optional.of(room));
        Mockito.when(this.availabilityService.areAvailable(any(), any(), any())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> this.frontDeskService.checkInWalkInGuest(rooms, Duration.between(LocalDateTime.now(), checkOutDate), guestDetails, rep, organizationId));

        Assertions.assertThrows(OccupiedRoomException.class, () -> {
            Mockito.when(this.availabilityService.areAvailable(any(), any(), any())).thenReturn(false);
            this.frontDeskService.checkInWalkInGuest(rooms, Duration.between(LocalDateTime.now(), checkOutDate), guestDetails, rep, organizationId);
        });

        Mockito.when(this.availabilityService.areAvailable(any(), any(), any())).thenReturn(true);

        Assertions.assertThrows(InvalidIdException.class, () -> {
            rooms.add(DomainFactory.createRoomId().toString());
            this.frontDeskService.checkInWalkInGuest(rooms, Duration.between(LocalDateTime.now(), checkOutDate), guestDetails, rep, organizationId);
        });

        Mockito.verify(this.stayRepository).put(this.actualStay.capture());

        Assertions.assertEquals(expected, this.actualStay.getValue());
    }

    @Test
    void given_Booking_when_CheckIn_then_CreateStay() {
        final Booking booking = DomainFactory.createBooking();
        final Room room = DomainFactory.createRoom();
        final List<String> rooms = new LinkedList<>();
        rooms.add(room.roomId().toString());
        final StayId stayId = DomainFactory.createStayId();
        final Set<Room> roomsSet = new HashSet<>();
        roomsSet.add(room);
        final Stay expected = Stay.create(stayId, LocalDateTime.now(), booking.checkOutDate(), roomsSet, booking.guestDetails(), booking.representativeDetails(), DomainFactory.createSeason(), new OrganizationId(""));

        Mockito.when(this.stayRepository.nextIdentity()).thenReturn(stayId);
        Mockito.when(this.roomRepository.findById(room.roomId())).thenReturn(Optional.of(room));
        Mockito.when(this.bookingRepository.findById(booking.bookingId())).thenReturn(Optional.of(booking));
        Mockito.when(this.availabilityService.areAvailable(any(), any(), any())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> this.frontDeskService.checkInWithBooking(rooms, Duration.between(LocalDateTime.now(), booking.checkOutDate()),
                booking.guestDetails(), booking.representativeDetails(), booking.organizationId(), booking.bookingId().toString()));


        Assertions.assertEquals(BookingState.checkedIn, booking.bookingState());

        Mockito.verify(this.stayRepository).put(this.actualStay.capture());

        Assertions.assertEquals(expected, this.actualStay.getValue());
    }



}

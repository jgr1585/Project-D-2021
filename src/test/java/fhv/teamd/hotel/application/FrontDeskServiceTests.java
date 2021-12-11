package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.BillDTO;
import fhv.teamd.hotel.application.dto.BillEntryDTO;
import fhv.teamd.hotel.application.dto.StayDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.application.exceptions.OccupiedRoomException;
import fhv.teamd.hotel.domain.*;
import fhv.teamd.hotel.domain.contactInfo.*;
import fhv.teamd.hotel.domain.ids.*;
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
import static org.mockito.ArgumentMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

        final GuestDetails guest = new GuestDetails("max", "muster", addr);

        final RepresentativeDetails rep = new RepresentativeDetails(
                "max","muster","m@mail.com", addr,"123456",
                "1111 1111 1111 1111", PaymentMethod.CreditCard);

        //noinspection deprecation
        final Category cat1 = new Category(1L, new CategoryId("Cat 1"), "Cat 1", "Cat 1", 20);
        //noinspection deprecation
        final Category cat2 = new Category(2L, new CategoryId("Cat 2"), "Cat 2", "Cat 2", 40);

        //noinspection deprecation
        final Room room1 = new Room(1L, new RoomId("Room 1"), cat1);
        final Set<Room> rooms1 = new HashSet<>();
        rooms1.add(room1);

        //noinspection deprecation
        final Room room2 = new Room(2L, new RoomId("Room 2"), cat2);
        final Set<Room> rooms2 = new HashSet<>();
        rooms2.add(room2);

        Stay stay1 = Stay.create(new StayId("stay-1"), yesterday, tomorrow, rooms1, guest, rep);
        Stay stay2 = Stay.create(new StayId("stay-2"), yesterday, now, rooms2, guest, rep);

        ReflectionTestUtils.setField(stay2, "stayingState", StayingState.CheckedOut);

        List<Stay> stays = new ArrayList<>(List.of(stay1, stay2));

        Mockito.when(this.stayRepository.getAll()).thenReturn(stays);
        Mockito.when(this.stayRepository.getActiveStays()).thenReturn(List.of(stay1));
        Mockito.when(this.stayRepository.find(new StayId("stay-1"))).thenReturn(Optional.of(stay1));
        Mockito.when(this.stayRepository.find(new StayId("stay-2"))).thenReturn(Optional.of(stay2));

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
    void given_stay_when_checkOut_then_returnBill() {

        StayDTO toCheckOut = this.frontDeskService.getActiveStays().get(0);

        final BillDTO[] billWrapper = new BillDTO[1];

        Assertions.assertDoesNotThrow(() -> {
            billWrapper[0] = this.frontDeskService.checkOut(toCheckOut.getId());
        });

        BillDTO bill = billWrapper[0];

        Assertions.assertEquals(20.0, bill.total());

        List<BillEntryDTO> entries = bill.entries();

        Assertions.assertEquals(1, entries.size());

        BillEntryDTO forNights = entries.get(0);

        Assertions.assertEquals(1, forNights.amount());
        Assertions.assertEquals(20.0, forNights.unitPrice());
        Assertions.assertEquals(20.0, forNights.subTotal());
        Assertions.assertNotNull(forNights.description());

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
        final Stay expected = Stay.create(stayId, LocalDateTime.now(), checkOutDate, roomsSet, guestDetails, rep);

        Mockito.when(this.stayRepository.nextIdentity()).thenReturn(stayId);
        Mockito.when(this.roomRepository.getById(room.roomId())).thenReturn(Optional.of(room));
        Mockito.when(this.availabilityService.areAvailable(any(), any(), any())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> this.frontDeskService.checkInWalkInGuest(rooms, Duration.between(LocalDateTime.now(), checkOutDate), guestDetails, rep));

        Assertions.assertThrows(OccupiedRoomException.class, () -> {
            Mockito.when(this.availabilityService.areAvailable(any(), any(), any())).thenReturn(false);
            this.frontDeskService.checkInWalkInGuest(rooms, Duration.between(LocalDateTime.now(), checkOutDate), guestDetails, rep);
        });

        Mockito.when(this.availabilityService.areAvailable(any(), any(), any())).thenReturn(true);

        Assertions.assertThrows(InvalidIdException.class, () -> {
            rooms.add(DomainFactory.createRoomId().toString());
            this.frontDeskService.checkInWalkInGuest(rooms, Duration.between(LocalDateTime.now(), checkOutDate), guestDetails, rep);
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
        final Stay expected = Stay.create(stayId, LocalDateTime.now(), booking.checkOutDate(), roomsSet, booking.guestDetails(), booking.representativeDetails());

        Mockito.when(this.stayRepository.nextIdentity()).thenReturn(stayId);
        Mockito.when(this.roomRepository.getById(room.roomId())).thenReturn(Optional.of(room));
        Mockito.when(this.bookingRepository.findByBookingId(booking.bookingId())).thenReturn(Optional.of(booking));
        Mockito.when(this.availabilityService.areAvailable(any(), any(), any())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> this.frontDeskService.checkInWithBooking(rooms, Duration.between(LocalDateTime.now(), booking.checkOutDate()),
                booking.guestDetails(), booking.representativeDetails(), booking.bookingId().toString()));


        Assertions.assertEquals(BookingState.checkedIn, booking.bookingState());

        Mockito.verify(this.stayRepository).put(this.actualStay.capture());

        Assertions.assertEquals(expected, this.actualStay.getValue());
    }

    @Test
    void given_Booking_when_get_intermediateBill_then_return_intermediateBill() {
        final Stay stay1 = DomainFactory.createStay();
        final Stay stay2 = DomainFactory.createStay();

        Mockito.when(this.stayRepository.find(stay1.stayId())).thenReturn(Optional.of(stay1));
        Mockito.when(this.stayRepository.find(stay2.stayId())).thenReturn(Optional.of(stay2));

        final AtomicReference<BillDTO> bill1 = new AtomicReference<>();
        final AtomicReference<BillDTO> bill2 = new AtomicReference<>();

        Assertions.assertDoesNotThrow(() -> bill1.set(this.frontDeskService.intermediateBill(stay1.stayId().toString())));

        Assertions.assertDoesNotThrow(() -> bill2.set(this.frontDeskService.intermediateBill(stay2.stayId().toString())));

        Assertions.assertThrows(InvalidIdException.class, () -> this.frontDeskService.intermediateBill(DomainFactory.createStayId().toString()));

        Assertions.assertEquals(BillDTO.fromBill(stay1.generateIntermediateBill()), bill1.get());
        Assertions.assertEquals(BillDTO.fromBill(stay2.generateIntermediateBill()), bill2.get());
    }

}

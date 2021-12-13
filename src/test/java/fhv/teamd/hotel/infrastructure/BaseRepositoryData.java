package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.*;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.RoomId;
import fhv.teamd.hotel.domain.ids.StayId;
import java.time.LocalDateTime;
import java.util.*;

//TODO: Finish
public class BaseRepositoryData {
    private static final List<Booking> bookings;
    private static final List<Category> categories;
    private static final List<Room> rooms;
    private static final List<Stay> stays;
    // private static final List<Bill> bills;
    // private static final List<BillEntry> billEntries;

    static {
        bookings = new LinkedList<>();
        categories = new LinkedList<>();
        rooms = new LinkedList<>();
        stays = new LinkedList<>();
        // bills = new LinkedList<>();
        // billEntries = new LinkedList<>();

        init();
    }

    @SuppressWarnings({ "deprecation", "SpellCheckingInspection" })
    private static void init() {
        final Category category1 = new Category(111L, new CategoryId("dom-id-cat-111"), "Single Bed", "hier könnte ihre werbung stehen", 50);
        final Category category2 = new Category(222L, new CategoryId("dom-id-cat-222"), "Double Bed", "........", 75);
        categories.addAll(List.of(category1, category2));

        final Address addressGuest1 = new Address("Musterstraße 5", "1234", "Dornbirn", "Austria");
        final Address addressRep1 = new Address("Musterstraße 5", "1234", "Dornbirn", "Austria");
        final Address addressGuest2 = new Address("Mustergasse 5",  "1234", "Feldkirch", "Austria");
        final Address addressRep2 = new Address("Musterstraße 5",  "1234", "Dornbirn", "Austria");

        final RepresentativeDetails rep1 = new RepresentativeDetails("Max", "Mustermann", "mustermann@mustermail.com", addressRep1, "123456789", "4111 1111 1111 1111", PaymentMethod.CreditCard);
        final RepresentativeDetails rep2 = new RepresentativeDetails("Max", "Musterfrau", "mustermann@mustermail.com", addressRep2, "123456789", "5555 5555 5555 4444", PaymentMethod.Cash);

        final GuestDetails gust1 = new GuestDetails("Max", "Mustermann", addressGuest1);
        final GuestDetails gust2 = new GuestDetails("Julia", "Musterfrau", addressGuest2);

        final Booking booking1 = new Booking(111L, new BookingId("dom-id-book-111"), LocalDateTime.parse("2021-12-26T10:00:00"), LocalDateTime.parse("2021-12-30T10:00:00"), Map.of(category1, 3), rep1, gust1);
        final Booking booking2 = new Booking(222L, new BookingId("dom-id-book-222"), LocalDateTime.parse("2022-12-26T10:00:00"), LocalDateTime.parse("2022-12-30T10:00:00"), Map.of(category1, 2, category2, 1), rep2, gust2);
        bookings.addAll(List.of(booking1, booking2));

        final Room room1 = new Room(111L, new RoomId("R111"), category1);
        final Room room2 = new Room(112L, new RoomId("R112"), category1);
        final Room room3 = new Room(113L, new RoomId("R113"), category1);
        final Room room4 = new Room(114L, new RoomId("R114"), category1);
        final Room room5 = new Room(115L, new RoomId("R115"), category1);
        final Room room6 = new Room(221L, new RoomId("R221"), category2);
        final Room room7 = new Room(222L, new RoomId("R222"), category2);
        final Room room8 = new Room(223L, new RoomId("R223"), category2);
        final Room room9 = new Room(224L, new RoomId("R224"), category2);
        final Room room10 = new Room(225L, new RoomId("R225"), category2);
        rooms.addAll(List.of(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10));

        final Address stayAddressGuest1 = new Address("Hans-Mauracher-Straße 162", "4117", "Wien", "Austria");
        final Address stayAddressRep1 = new Address("Hans-Mauracher-Straße 162", "4117", "Wien", "Austria");
        final Address stayAddressGuest2 = new Address("Gösting 13b",  "9542", "Linz", "Austria");
        final Address stayAddressRep2 = new Address("Gösting 13b",  "9542", "Linz", "Austria");

        final RepresentativeDetails stayRep1 = new RepresentativeDetails("John", "Thompson", "j.thompson@randatmail.com", stayAddressRep1, "137-3936-04", "1111 1111 1111 1111", PaymentMethod.CreditCard);
        final RepresentativeDetails stayRep2 = new RepresentativeDetails("Emma", "Ross", "e.ross@randatmail.com", stayAddressRep2, "559-1716-40", "2222 2222 2222 2222", PaymentMethod.Cash);

        final GuestDetails stayGust1 = new GuestDetails("John", "Thompson", stayAddressGuest1);
        final GuestDetails stayGust2 = new GuestDetails("Emma", "Ross", stayAddressGuest2);

        final Stay stay1 = new Stay(111L, new StayId("dom-id-stay-111"), LocalDateTime.parse("2021-11-13T10:00:00"), LocalDateTime.parse("2021-11-20T10:00:00"), Set.of(room4, room5), stayRep1, stayGust1, StayingState.CheckedIn);
        final Stay stay2 = new Stay(222L, new StayId("dom-id-stay-222"), LocalDateTime.parse("2021-11-10T10:00:00"), LocalDateTime.parse("2021-11-15T10:00:00"), Set.of(room8), stayRep2, stayGust2, StayingState.CheckedIn);
        stays.addAll(List.of(stay1, stay2));

        //TODO: Data for Bill

    }

    public static List<Booking> bookings() {
        return Collections.unmodifiableList(bookings);
    }

    public static List<Category> categories() {
        return Collections.unmodifiableList(categories);
    }

    public static List<Room> rooms() {
        return Collections.unmodifiableList(rooms);
    }

    public static List<Stay> stays() {
        return Collections.unmodifiableList(stays);
    }

//    public static List<Bill> bills() {
//        return Collections.unmodifiableList(bills);
//    }
//
//    public static List<BillEntry> billEntries() {
//        return Collections.unmodifiableList(billEntries);
//    }

}

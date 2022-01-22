package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.*;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

//TODO: Finish
public abstract class BaseRepositoryData {
    private static final List<Booking> bookings;
    private static final List<Category> categories;
    private static final List<Room> rooms;
    private static final List<Stay> stays;
    private static final List<Season> seasons;
    private static final List<Organization> organizations;
    // private static final List<Bill> bills;
    // private static final List<BillEntry> billEntries;

    static {
        bookings = new LinkedList<>();
        categories = new LinkedList<>();
        rooms = new LinkedList<>();
        stays = new LinkedList<>();
        seasons = new LinkedList<>();
        organizations = new LinkedList<>();
        // bills = new LinkedList<>();
        // billEntries = new LinkedList<>();

        init();
    }

    @SuppressWarnings({ "deprecation", "SpellCheckingInspection" })
    private static void init() {
        final Season season1 = new Season(1L, new SeasonId("Season1"), "Summer", Month.MAY, Month.OCTOBER);
        final Season season2 = new Season(2L, new SeasonId("Season2"), "Winter", Month.NOVEMBER, Month.APRIL);
        seasons.addAll(List.of(season1, season2));

        final Map<Season, Double> pricePerNight1 = Map.of(season1, 60.0, season2, 75.0);
        final Map<Season, Double> pricePerNight2 = Map.of(season1, 120.0, season2, 150.0);
        final Map<Season, Double> pricePerNight3 = Map.of(season1, 200.0, season2, 250.0);
        final Map<Season, Double> pricePerNight4 = Map.of(season1, 400.0, season2, 500.0);

        final Category category1 = new Category(111L, new CategoryId("dom-id-cat-111"), "Single Room", "A room assigned to one person with one bed.", pricePerNight1);
        final Category category2 = new Category(222L, new CategoryId("dom-id-cat-222"), "Double Room", "A room assigned to two persons with a double bed.", pricePerNight2);
        final Category category3 = new Category(333L, new CategoryId("dom-id-cat-333"), "Family Room", "A room assigned to a maximum of five persons with a mixture of single and double beds.", pricePerNight3);
        final Category category4 = new Category(333L, new CategoryId("dom-id-cat-333"), "Suite", "A luxurious apartment assigned to a maximum of six persons with several bedrooms.", pricePerNight4);
        categories.addAll(List.of(category1, category2, category3, category4));

        final Address addressGuest1 = new Address("Bachgasse 5", "6850", "Dornbirn", "Austria");
        final Address addressRep1 = new Address("Bachgasse 5", "6850", "Dornbirn", "Austria");
        final Address addressGuest2 = new Address("Naflastraße 1", "6800", "Feldkirch", "Austria");
        final Address addressRep2 = new Address("Naflastraße 1", "6800", "Feldkirch", "Austria");
        final Address addressGuest3 = new Address("3278 Alfred Drive", "11201", "New York", "United States");
        final Address addressRep3 = new Address("3278 Alfred Drive", "11201", "New York", "United States");

        final RepresentativeDetails rep1 = new RepresentativeDetails("Peter", "Mayer", "p.mayer@vol.at", addressRep1, "0699987654", "4111 1111 1111 1111", PaymentMethod.CreditCard);
        final RepresentativeDetails rep2 = new RepresentativeDetails("Eva", "Müller", "eva.m@hotmail.com", addressRep2, "0664123456", "4111 1111 1111 1111", PaymentMethod.Cash);
        final RepresentativeDetails rep3 = new RepresentativeDetails("John", "Smith", "smith@gmail.com", addressRep3, "718-254-5328", "5555 5555 5555 4444", PaymentMethod.Cash);

        final GuestDetails gust1 = new GuestDetails("Peter", "Mayer", addressGuest1);
        final GuestDetails gust2 = new GuestDetails("Eva", "Müller", addressGuest2);
        final GuestDetails gust3 = new GuestDetails("John", "Smith", addressGuest3);

        final Booking booking1 = new Booking(111L, new BookingId("dom-id-book-111"), LocalDateTime.parse("2022-02-01T10:00:00"), LocalDateTime.parse("2021-03-01T10:00:00"), Map.of(category1, 3), rep1, gust1, new OrganizationId(""));
        final Booking booking2 = new Booking(222L, new BookingId("dom-id-book-222"), LocalDateTime.parse("2022-12-26T10:00:00"), LocalDateTime.parse("2022-12-30T10:00:00"), Map.of(category1, 2, category2, 1), rep2, gust2, new OrganizationId("dom-id-org-111"));
        final Booking booking3 = new Booking(333L, new BookingId("dom-id-book-333"), LocalDateTime.parse("2022-03-01T10:00:00"), LocalDateTime.parse("2022-03-10T10:00:00"), Map.of(category3, 1), rep3, gust3, new OrganizationId(""));
        bookings.addAll(List.of(booking1, booking2, booking3));

        final Room room01 = new Room(111L, new RoomId("R111"), category1);
        final Room room02 = new Room(112L, new RoomId("R112"), category1);
        final Room room03 = new Room(113L, new RoomId("R113"), category1);
        final Room room04 = new Room(114L, new RoomId("R114"), category1);
        final Room room05 = new Room(115L, new RoomId("R115"), category1);
        final Room room06 = new Room(116L, new RoomId("R116"), category1);
        final Room room07 = new Room(117L, new RoomId("R117"), category1);
        final Room room08 = new Room(118L, new RoomId("R118"), category1);
        final Room room09 = new Room(119L, new RoomId("R119"), category1);
        final Room room10 = new Room(120L, new RoomId("R120"), category1);

        final Room room11 = new Room(221L, new RoomId("R221"), category2);
        final Room room12 = new Room(222L, new RoomId("R222"), category2);
        final Room room13 = new Room(223L, new RoomId("R223"), category2);
        final Room room14 = new Room(224L, new RoomId("R224"), category2);
        final Room room15 = new Room(225L, new RoomId("R225"), category2);
        final Room room16 = new Room(226L, new RoomId("R226"), category2);
        final Room room17 = new Room(227L, new RoomId("R227"), category2);
        final Room room18 = new Room(228L, new RoomId("R228"), category2);
        final Room room19 = new Room(229L, new RoomId("R229"), category2);
        final Room room20 = new Room(230L, new RoomId("R230"), category2);
        final Room room21 = new Room(231L, new RoomId("R231"), category2);
        final Room room22 = new Room(232L, new RoomId("R232"), category2);
        final Room room23 = new Room(233L, new RoomId("R233"), category2);
        final Room room24 = new Room(234L, new RoomId("R234"), category2);
        final Room room25 = new Room(235L, new RoomId("R235"), category2);

        final Room room26 = new Room(331L, new RoomId("R331"), category3);
        final Room room27 = new Room(332L, new RoomId("R332"), category3);
        final Room room28 = new Room(333L, new RoomId("R333"), category3);
        final Room room29 = new Room(334L, new RoomId("R334"), category3);
        final Room room30 = new Room(335L, new RoomId("R335"), category3);

        final Room room31 = new Room(441L, new RoomId("R441"), category4);
        final Room room32 = new Room(442L, new RoomId("R442"), category4);
        final Room room33 = new Room(443L, new RoomId("R443"), category4);

        rooms.addAll(List.of(
                room01, room02, room03, room04, room05, room06, room07, room08, room09, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, room21, room22, room23, room24, room25,
                room26, room27, room28, room29, room30,
                room31, room32, room33
        ));

        final Address stayAddressGuest1 = new Address("Hans-Mauracher-Straße 162", "4117", "Wien", "Austria");
        final Address stayAddressRep1 = new Address("Hans-Mauracher-Straße 162", "4117", "Wien", "Austria");
        final Address stayAddressGuest2 = new Address("Gösting 13b", "9542", "Linz", "Austria");
        final Address stayAddressRep2 = new Address("Gösting 13b", "9542", "Linz", "Austria");

        final RepresentativeDetails stayRep1 = new RepresentativeDetails("Jonas", "Tiefenthaler", "j.thiefenthaler@outlook.com", stayAddressRep1, "069954321", "1111 1111 1111 1111", PaymentMethod.CreditCard);
        final RepresentativeDetails stayRep2 = new RepresentativeDetails("Emma", "Bauer", "emma.b@outlook.com", stayAddressRep2, "0664123987", "2222 2222 2222 2222", PaymentMethod.Cash);

        final GuestDetails stayGust1 = new GuestDetails("Jonas", "Tiefenthaler", stayAddressGuest1);
        final GuestDetails stayGust2 = new GuestDetails("Emma", "Bauer", stayAddressGuest2);

        final Stay stay1 = new Stay(111L, new StayId("dom-id-stay-111"), LocalDateTime.parse("2022-01-20T10:00:00"), LocalDateTime.parse("2022-02-01T10:00:00"), Set.of(room04, room05), stayRep1, stayGust1, StayingState.CheckedIn, new OrganizationId(""), new BillId("146567"));
        final Stay stay2 = new Stay(222L, new StayId("dom-id-stay-222"), LocalDateTime.parse("2022-01-20T10:00:00"), LocalDateTime.parse("2022-02-10T10:00:00"), Set.of(room08), stayRep2, stayGust2, StayingState.CheckedIn, new OrganizationId("dom-id-org-111"), new BillId("7569526"));
        stays.addAll(List.of(stay1, stay2));

        final Address orgAddress1 = new Address("Im Städtle 40", "6973", "Höchst", "Austria");
        final Address orgAddress2 = new Address("Konrad-Doppelmayr-Straße 1", "6922", "Wolfurt", "Austria");
        final Address orgAddress3 = new Address("Kreuzäckerweg 33", "6800", "Feldkirch", "Austria");

        final Organization org1 = new Organization(111L, new OrganizationId("dom-id-org-111"), "Blum GmbH", orgAddress1, 15);
        final Organization org2 = new Organization(222L, new OrganizationId("dom-id-org-222"), "Doppelmayr/Garaventa Group", orgAddress2, 10);
        final Organization org3 = new Organization(333L, new OrganizationId("dom-id-org-333"), "Bachmann electronic GmbH", orgAddress3, 5);
        organizations.addAll(List.of(org1, org2, org3));


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

    public static List<Season> seasons() {
        return Collections.unmodifiableList(seasons);
    }

    public static List<Organization> organizations() {
        return Collections.unmodifiableList(organizations);
    }

    //    public static List<Bill> bills() {
//        return Collections.unmodifiableList(bills);
//    }
//
//    public static List<BillEntry> billEntries() {
//        return Collections.unmodifiableList(billEntries);
//    }

}

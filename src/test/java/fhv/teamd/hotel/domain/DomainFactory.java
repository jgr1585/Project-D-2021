package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.RoomId;
import fhv.teamd.hotel.domain.ids.StayId;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class DomainFactory {

    public static Address createAddress() {
        UUID uuid = UUID.randomUUID();

        return new Address("Street" + uuid, uuid.toString(), "City" + uuid, "C" + uuid);
    }

    public static CategoryId createCategoryId() {
        return createCategoryId(UUID.randomUUID());
    }

    private static CategoryId createCategoryId(UUID uuid) {
        return new CategoryId(uuid.toString());
    }

    public static Category createCategory() {
        UUID uuid = UUID.randomUUID();

        return new Category(uuidToLong(uuid), createCategoryId(uuid), "Category " + uuid, "Category " + uuid, 20);
    }

    public static BookingId createBookingId() {
        return createBookingId(UUID.randomUUID());
    }

    private static BookingId createBookingId(UUID uuid) {
        return new BookingId(uuid.toString());
    }

    public static Booking createBooking() {
        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = LocalDateTime.now().plus(Period.ofDays(1));
        RepresentativeDetails rep = createRepresentativeDetails();

        Map<Category, Integer> cats = new HashMap<>();
        cats.put(createCategory(), 1);

        return new Booking(createBookingId(uuid), now, tomorrow, cats, rep, getFromRepresentativeDetails(rep));
    }

    public static RepresentativeDetails createRepresentativeDetails() {
        UUID uuid = UUID.randomUUID();

        return new RepresentativeDetails("John the " + uuid, "Doe", "john" + uuid + ".doe@mail.com", createAddress(),"0" + uuid, "1111 1111 1111 1111", PaymentMethod.CreditCard);
    }

    public static RoomId createRoomId() {
        return createRoomId(UUID.randomUUID());
    }

    private static RoomId createRoomId(UUID uuid) {
        return new RoomId(uuid.toString());
    }

    public static Room createRoom() {
        return createRoomInCategory(createCategory());
    }

    public static Room createRoomInCategory(Category category) {
        UUID uuid = UUID.randomUUID();

        return new Room(uuidToLong(uuid), createRoomId(uuid), category);
    }

    public static StayId createStayId() {
        return createStayId(UUID.randomUUID());
    }

    private static StayId createStayId(UUID uuid) {
        return new StayId(uuid.toString());
    }

    public static GuestDetails getFromRepresentativeDetails(RepresentativeDetails rep) {
        return new GuestDetails(rep.firstName(), rep.lastName(), rep.address());
    }

    private static long uuidToLong(UUID uuid) {
        return uuid.getMostSignificantBits();
    }
}

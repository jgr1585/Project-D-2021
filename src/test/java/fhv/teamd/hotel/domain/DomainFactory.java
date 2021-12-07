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

    public static Address CreateAddress() {
        UUID uuid = UUID.randomUUID();

        return new Address("Street" + uuid, uuid.toString(), "City" + uuid, "C" + uuid);
    }

    public static CategoryId CreateCategoryId() {
        return CreateCategoryId(UUID.randomUUID());
    }

    private static CategoryId CreateCategoryId(UUID uuid) {
        return new CategoryId(uuid.toString());
    }

    public static Category CreateCategory() {
        UUID uuid = UUID.randomUUID();

        return new Category(uuidToLong(uuid), CreateCategoryId(uuid), "Category " + uuid, "Category " + uuid, 20);
    }

    public static BookingId CreateBookingId() {
        return CreateBookingId(UUID.randomUUID());
    }

    private static BookingId CreateBookingId(UUID uuid) {
        return new BookingId(uuid.toString());
    }

    public static Booking CreateBooking() {
        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = LocalDateTime.now().plus(Period.ofDays(1));
        RepresentativeDetails rep = CreateRepresentativeDetails();

        Map<Category, Integer> cats = new HashMap<>();
        cats.put(CreateCategory(), 1);

        return new Booking(CreateBookingId(uuid), now, tomorrow, cats, rep, GetFromRepresentativeDetails(rep));
    }

    public static RepresentativeDetails CreateRepresentativeDetails() {
        UUID uuid = UUID.randomUUID();

        return new RepresentativeDetails("John the " + uuid, "Doe", "john" + uuid + ".doe@mail.com", CreateAddress(),"0" + uuid, "1111 1111 1111 1111", PaymentMethod.CreditCard);
    }


    public static RoomId CreateRoomId() {
        return CreateRoomId(UUID.randomUUID());
    }

    private static RoomId CreateRoomId(UUID uuid) {
        return new RoomId(uuid.toString());
    }

    public static Room CreateRoom() {
        return CreateRoomInCategory(CreateCategory());
    }

    public static Room CreateRoomInCategory(Category category) {
        UUID uuid = UUID.randomUUID();

        return new Room(uuidToLong(uuid), CreateRoomId(uuid), category);
    }

    public static StayId CreateStayId() {
        return CreateStayId(UUID.randomUUID());
    }

    private static StayId CreateStayId(UUID uuid) {
        return new StayId(uuid.toString());
    }

    public static GuestDetails GetFromRepresentativeDetails(RepresentativeDetails rep) {
        return new GuestDetails(rep.firstName(), rep.lastName(), rep.address());
    }

    private static long uuidToLong(UUID uuid) {
        return uuid.getMostSignificantBits();
    }

}

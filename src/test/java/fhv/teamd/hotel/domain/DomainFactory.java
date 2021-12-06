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
import java.util.Random;

@SuppressWarnings("deprecation")
public class DomainFactory {

    private static final Random r;

    static {
        r = new Random();
    }

    public static Address CreateAddress() {
        int num = r.nextInt();

        return new Address("Street" + num, Integer.toString(num), "City" + num, "C" + num);
    }

    public static CategoryId CreateCategoryId() {
        return CreateCategoryId(r.nextInt());
    }

    private static CategoryId CreateCategoryId(int i) {
        return new CategoryId("Category " + i);
    }

    public static Category CreateCategory() {
        int num = r.nextInt();

        return new Category((long) num, CreateCategoryId(num), "Category " + num, "Category " + num, 20);
    }

    public static BookingId CreateBookingId() {
        return CreateBookingId(r.nextInt());
    }

    private static BookingId CreateBookingId(int i) {
        return new BookingId("Category " + i);
    }

    public static Booking CreateBooking() {
        int num = r.nextInt();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = LocalDateTime.now().plus(Period.ofDays(1));
        RepresentativeDetails rep = CreateRepresentativeDetails();

        Map<Category, Integer> cats = new HashMap<>();
        cats.put(CreateCategory(), 1);

        return new Booking(CreateBookingId(num), now, tomorrow, cats, rep, GetFromRepresentativeDetails(rep));
    }

    public static RepresentativeDetails CreateRepresentativeDetails() {
        int num = r.nextInt();

        return new RepresentativeDetails("John the " + num + "th", "Doe", "john.doe" + num + "@mail.com", CreateAddress(),"0" + num, "1111 1111 1111 1111", PaymentMethod.CreditCard);
    }


    public static RoomId CreateRoomId() {
        return CreateRoomId(r.nextInt());
    }

    private static RoomId CreateRoomId(int i) {
        return new RoomId("Room " + i);
    }

    public static Room CreateRoom() {
        return CreateRoomInCategory(CreateCategory());
    }

    public static Room CreateRoomInCategory(Category category) {
        int num = r.nextInt();

        return new Room((long) num, CreateRoomId(num), category);
    }

    public static StayId CreateStayId() {
        return CreateStayId(r.nextInt());
    }

    private static StayId CreateStayId(int i) {
        return new StayId("Stay " + i);
    }

    public static GuestDetails GetFromRepresentativeDetails(RepresentativeDetails rep) {
        return new GuestDetails(rep.firstName(), rep.lastName(), rep.address());
    }


}

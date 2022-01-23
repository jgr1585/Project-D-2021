package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.BookingState;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class HibernateBookingRepository extends HibernateBaseRepository<Booking, BookingId> implements BookingRepository {

    @Override
    public List<Booking> getActiveBookings() {
        return this.entityManager
                .createQuery("SELECT b FROM Booking b where b.bookingState = :state", Booking.class)
                .setParameter("state", BookingState.booked)
                .getResultList();
    }

    @Override
    public int numberOfBookedRoomsByCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until) {

        Long l = this.entityManager.createQuery(
                        "select sum(value(s)) from Booking b " +
                                "join b.categories s " +
                                "where b.checkInDate < :until and b.checkOutDate > :from " +
                                "and key(s).domainId = :catId",
                        Long.class)
                .setParameter("from", from)
                .setParameter("until", until)
                .setParameter("catId", categoryId)
                .getSingleResult();

        return Optional.ofNullable(l).map(Long::intValue).orElse(0);
    }

    @Override
    public void put(Booking booking) {

        this.entityManager.persist(booking);

    }

}

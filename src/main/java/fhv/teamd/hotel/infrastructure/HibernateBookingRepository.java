package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class HibernateBookingRepository implements BookingRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public BookingId nextIdentity() {
        return new BookingId(java.util.UUID.randomUUID().toString());
    }

    @Override
    public List<Booking> getAllBookings() {
        return this.entityManager
                .createQuery("SELECT b FROM Booking b", Booking.class)
                .getResultList();
    }

    @Override
    public Optional<Booking> findByBookingId(BookingId bookingId) {
        TypedQuery<Booking> q = this.entityManager
                .createQuery("SELECT b FROM Booking b WHERE b.bookingId = :id", Booking.class);

        q.setParameter("id", bookingId);

        return Optional.ofNullable(q.getSingleResult());
    }

    @Override
    public List<Booking> getBookingsByCheckInDate(LocalDateTime from, LocalDateTime until) {
        TypedQuery<Booking> q = this.entityManager.createQuery(
                "SELECT b FROM Booking b WHERE b.checkIn > :from AND b.checkOut < :until",
                Booking.class);

        q.setParameter("from", from);
        q.setParameter("until", until);

        return q.getResultList();
    }
}

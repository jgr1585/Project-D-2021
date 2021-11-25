package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.Category;
import org.springframework.stereotype.Repository;
import java.util.Map;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class HibernateBookingRepository implements BookingRepository {

    @PersistenceContext
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

        return q.getResultStream().findFirst();
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

    @Override
    public int getNumberOfBookedRoomsByCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until) {

        int numberOfRooms = 0;

        for (Booking booking : this.getBookingsByCheckInDate(from, until)) {

            for (Map.Entry<Category, Integer> categoryIntegerEntry : booking.selection().entrySet()) {

                if (categoryIntegerEntry.getKey().categoryId().equals(categoryId)) {

                    numberOfRooms += categoryIntegerEntry.getValue();
                }
            }
        }

        return numberOfRooms;

        //return this.getBookingsByCheckInDate(from, until)
        //        .stream().flatMap(booking -> booking.selection().entrySet().stream())
        //        .filter(entry -> entry.getKey().categoryId().equals(categoryId))
        //        .map(Map.Entry::getValue)
        //        .reduce(Integer::sum)
        //        .orElse(0);

    }

    @Override
    public void put(Booking booking) {

        this.entityManager.persist(booking);

    }

    @Override
    public void remove(BookingId bookingId) throws EntityNotFoundException {

        Query q = this.entityManager.createQuery(
                "DELETE FROM Booking b WHERE b.bookingId = :id");

        q.setParameter("id", bookingId);

        if (q.executeUpdate() != 1) {
            throw new EntityNotFoundException();
        }

    }
}

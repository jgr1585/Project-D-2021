package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.ContactInfo;
import fhv.teamd.hotel.domain.GuestInfo;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void book(Map<String, Integer> categoryIdsAndAmounts,
                     LocalDateTime from, LocalDateTime until,
                     GuestDetailsDTO guest, RepresentativeDetailsDTO rep) {

        Map<Category, Integer> categoriesAndAmounts = new HashMap<>();

        for (Map.Entry<String, Integer> entry : categoryIdsAndAmounts.entrySet()) {

            String id = entry.getKey();
            Integer count = entry.getValue();

            if (count == null || count == 0) {
                continue;
            }

            Optional<Category> result = this.categoryRepository.findById(new CategoryId(id));
            if (result.isEmpty()) {
                throw new EntityNotFoundException("no category with this id");
            }

            Category cat = result.get();

            categoriesAndAmounts.put(cat, count);
        }

        GuestInfo guestInfo = new GuestInfo(
                String.join(" ", guest.firstName(), guest.lastName()),
                String.join(" ", guest.street(), guest.zip(), guest.city(), guest.country()));

        ContactInfo contactInfo = new ContactInfo(
                String.join(" ", rep.firstName(), rep.lastName()),
                rep.email(),
                String.join(" ", rep.street(), rep.zip(), rep.city(), rep.country()),
                rep.phone()
        );

        Booking newBooking = new Booking(
                this.bookingRepository.nextIdentity(),
                from, until, categoriesAndAmounts, contactInfo, guestInfo);

        this.bookingRepository.put(newBooking);
    }

    @Override
    public List<BookingDTO> getAll() {
        return this.bookingRepository
                .getAllBookings()
                .stream()
                .map(BookingDTO::fromBooking)
                .collect(Collectors.toUnmodifiableList());

    }

    @Override
    public Optional<DetailedBookingDTO> getDetails(String bookingId) {

        Optional<Booking> result = this.bookingRepository.findByBookingId(new BookingId(bookingId));
        if (result.isEmpty()) {
            return Optional.empty();
        }

        Booking booking = result.get();

        HashMap<CategoryDTO, Integer> categories = new HashMap<>();

        for (Map.Entry<Category, Integer> entry : booking.selection().entrySet()) {
            categories.put(CategoryDTO.fromCategory(entry.getKey()), entry.getValue());
        }

        return Optional.of(new DetailedBookingDTO(BookingDTO.fromBooking(booking), categories));
    }
}

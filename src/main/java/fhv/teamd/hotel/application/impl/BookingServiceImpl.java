package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.domain.*;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void book(Map<String, Integer> categoryIdsAndAmounts,
                     LocalDateTime from, LocalDateTime until,
                     GuestDetails guest, RepresentativeDetails rep) throws Exception {

        Map<Category, Integer> categoriesAndAmounts = new HashMap<>();

        for (Map.Entry<String, Integer> entry : categoryIdsAndAmounts.entrySet()) {

            String id = entry.getKey();
            Integer count = entry.getValue();

            if (count == null || count == 0) {
                continue;
            }

            Optional<Category> result = this.categoryRepository.findById(new CategoryId(id));
            if (result.isEmpty()) {
                throw new Exception("no category with this id");
            }

            Category cat = result.get();

            categoriesAndAmounts.put(cat, count);
        }

        Booking newBooking = new Booking(
                this.bookingRepository.nextIdentity(),
                from, until, categoriesAndAmounts, rep, guest);

        this.bookingRepository.put(newBooking);
    }

    @Override
    @Transactional
    public List<BookingDTO> getAll() {
        return this.bookingRepository
                .getAllBookings()
                .stream()
                .map(BookingDTO::fromBooking)
                .collect(Collectors.toUnmodifiableList());

    }

    @Override
    @Transactional
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

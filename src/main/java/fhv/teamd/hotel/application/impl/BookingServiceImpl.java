package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.application.exceptions.CategoryNotAvailableException;
import fhv.teamd.hotel.domain.*;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import fhv.teamd.hotel.domain.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AvailabilityService availabilityService;


    @Override
    @Transactional
    public void book(Map<String, Integer> categoryIdsAndAmounts,
                     LocalDateTime from, LocalDateTime until,
                     GuestDetails guest, RepresentativeDetails rep) throws Exception {

        Map<Category, Integer> categoriesAndAmounts = new HashMap<>();

        for (Map.Entry<String, Integer> entry : categoryIdsAndAmounts.entrySet()) {

            String id = entry.getKey();
            Integer amount = entry.getValue();

            if (amount == null || amount == 0) {
                continue;
            }

            CategoryId categoryId = new CategoryId(id);
            Optional<Category> result = this.categoryRepository.findById(categoryId);
            if (result.isEmpty()) {
                throw new Exception("no category with this id");
            }

            if (!this.availabilityService.isAvailableCategory(categoryId, from, until, amount)) {
                throw new CategoryNotAvailableException("category not available");
            }

            Category cat = result.get();

            categoriesAndAmounts.put(cat, amount);
        }

        Booking newBooking = new Booking(
                this.bookingRepository.nextIdentity(),
                from, until, categoriesAndAmounts, rep, guest);

        this.bookingRepository.put(newBooking);
    }

    @Override
    @Transactional
    public List<BookingDTO> getActiveBookings() {
        return this.bookingRepository
                .getActiveBookings()
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

package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    private BookingDTO toDTO(Booking booking) {
        return BookingDTO.builder()
                .withId(booking.bookingId().toString())
                .withRepresentativeName(booking.contactInfo().name())
                .withFromDate(booking.checkInDate())
                .withUntilDate(booking.checkOutDate())
                .build();
    }

    private CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .withId(category.categoryId().toString())
                .withTitle(category.title())
                .withDescription(category.description())
                .withPrice(category.pricePerNight())
                .build();
    }


    @Override
    public void book(RequestedStayDTO requestedStay, GuestDetailsDTO customerData) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<BookingDTO> getAll() {
        return this.bookingRepository
                .getAllBookings()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toUnmodifiableList());

    }

    @Override
    public Optional<DetailedBookingDTO> getDetails(String bookingId) {

        Optional<Booking> result = this.bookingRepository.findByBookingId(new BookingId(bookingId));
        if(result.isEmpty()) {
            return Optional.empty();
        }

        Booking booking = result.get();

        HashMap<CategoryDTO, Integer> categories = new HashMap<>();

        for(Map.Entry<Category, Integer> entry: booking.selection().entrySet()) {
            categories.put(this.toDTO(entry.getKey()), entry.getValue());
        }

        return Optional.of(new DetailedBookingDTO(this.toDTO(booking), categories));
    }
}

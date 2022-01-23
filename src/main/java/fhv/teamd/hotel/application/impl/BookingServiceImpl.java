package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.dto.*;
import fhv.teamd.hotel.application.dto.contactInfo.GuestDetailsDTO;
import fhv.teamd.hotel.application.dto.contactInfo.RepresentativeDetailsDTO;
import fhv.teamd.hotel.application.exceptions.CategoryNotAvailableException;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.domain.*;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.OrganizationId;
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
    public void book(BookingDTO bookingDTO) throws Exception {
        OrganizationId organizationId = bookingDTO.organizationId() == null ? null : new OrganizationId(bookingDTO.organizationId());

        this.book(bookingDTO.categories(), bookingDTO.fromDate().atStartOfDay(), bookingDTO.untilDate().atStartOfDay(), bookingDTO.guest(), bookingDTO.representative(), organizationId);
    }


    @Override
    @Transactional
    public void book(Map<String, Integer> categoryIdsAndAmounts,
                     LocalDateTime from, LocalDateTime until,
                     GuestDetails guest, RepresentativeDetails rep, OrganizationId orgId) throws Exception {

        this.book(categoryIdsAndAmounts, from, until, GuestDetailsDTO.fromGuestDetail(guest), RepresentativeDetailsDTO.fromRepresentativeDetails(rep), orgId);
    }


    private void book(Map<String, Integer> categoryIdsAndAmounts,
                     LocalDateTime from, LocalDateTime until,
                     GuestDetailsDTO guest, RepresentativeDetailsDTO rep, OrganizationId orgId) throws Exception {

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
                throw new InvalidIdException("no category with this id");
            }

            if (!this.availabilityService.isAvailable(categoryId, from, until, amount)) {
                throw new CategoryNotAvailableException("category not available");
            }

            Category cat = result.get();

            categoriesAndAmounts.put(cat, amount);
        }

        Address repAddress = new Address(rep.address().street(), rep.address().zip(), rep.address().city(), rep.address().country());
        RepresentativeDetails representativeDetails = new RepresentativeDetails(rep.firstName(), rep.lastName(), rep.email(), repAddress, rep.phone(), rep.creditCardNumber(), rep.paymentMethod());

        Address guestAddress = new Address(guest.address().street(), guest.address().zip(), guest.address().city(), guest.address().country());
        GuestDetails guestDetails = new GuestDetails(guest.firstName(), guest.lastName(), guestAddress);

        Booking newBooking = new Booking(
                this.bookingRepository.nextIdentity(),
                from, until, categoriesAndAmounts, representativeDetails, guestDetails, orgId);

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

        Optional<Booking> result = this.bookingRepository.findById(new BookingId(bookingId));

        return result.map(DetailedBookingDTO::fromBooking);
    }
}

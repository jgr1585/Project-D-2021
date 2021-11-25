package fhv.teamd.hotel.domain.services.impl;

import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import fhv.teamd.hotel.domain.services.CategoryAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryAvailabiltyServiceImpl implements CategoryAvailabilityService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public int getAmountOfAvailableCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until) {

        Optional<Category> categories = this.categoryRepository.findById(categoryId);

        int amount = this.bookingRepository.getNumberOfBookedRoomsByCategory(categoryId, from, until);


        return 0;
    }

    @Override
    public boolean isAvailable(Map.Entry<String, Integer> categoryIdsAndAmounts, LocalDateTime from, LocalDateTime until, int amount) {


        return false;
    }
}

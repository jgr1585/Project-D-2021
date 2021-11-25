package fhv.teamd.hotel.domain.services;

import fhv.teamd.hotel.domain.ids.CategoryId;

import java.time.LocalDateTime;
import java.util.Map;

public interface CategoryAvailabilityService {

    int getAmountOfAvailableCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until);

    boolean isAvailable(Map.Entry<String, Integer> categoryIdsAndAmounts, LocalDateTime from, LocalDateTime until, int amount);

}

package fhv.teamd.hotel.domain.services.impl;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.Selection;
import fhv.teamd.hotel.domain.services.AvailabilityService;

import java.time.LocalDateTime;

public class AvailabilityServiceInfiniteHotelImpl implements AvailabilityService {

    @Override
    public boolean checkAvailability(Selection selection, LocalDateTime from, LocalDateTime until) {
        return true;
    }

    @Override
    public int countAvailable(Category category, LocalDateTime from, LocalDateTime until) {
        return 999;
    }
}

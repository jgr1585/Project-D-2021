package fhv.teamd.hotel.domain.services;

import fhv.teamd.hotel.domain.Selection;

import java.time.LocalDateTime;

public interface AvailabilityService {

    boolean checkAvailability(Selection selection, LocalDateTime from, LocalDateTime until);

}

package fhv.teamd.hotel.application;


import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;

import java.time.Duration;
import java.util.List;

public interface FrontDeskService {

    void checkIn(List<String> roomIds, Duration expectedDuration,
                 GuestDetails guest, RepresentativeDetails representative);

}

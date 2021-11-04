package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.PersonalDetailsDTO;
import fhv.teamd.hotel.application.dto.RequestedStayDTO;

public interface BookingService {

    void book(RequestedStayDTO requestedStay, PersonalDetailsDTO customerData);

}

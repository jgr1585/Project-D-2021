package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.GuestDetailsDTO;
import fhv.teamd.hotel.application.dto.RequestedStayDTO;

public interface BookingService {

    void book(RequestedStayDTO requestedStay, GuestDetailsDTO customerData);

}

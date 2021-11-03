package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.CustomerDataDTO;
import fhv.teamd.hotel.application.dto.RequestedStayDTO;

public interface BookingService {

    void book(RequestedStayDTO requestedStay, CustomerDataDTO customerData);

}

package fhv.teamd.hotel.rest;

import fhv.teamd.hotel.application.BookingService;
import fhv.teamd.hotel.application.dto.BookingDTO;
import fhv.teamd.hotel.domain.DomainFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private BookingService categoryBookingService;

    @Captor
    private ArgumentCaptor<BookingDTO> actualBooking;

    private StringBuilder localAddress;

    @SuppressWarnings("HttpUrlsUsage")
    @BeforeEach
    void init() {
        this.localAddress = new StringBuilder();
        this.localAddress.append("http://");
        this.localAddress.append("localhost:");
        this.localAddress.append(this.port);
        this.localAddress.append("/rest/booking");
    }

    @Test
    void given_bookingDTO_when_book_then_create_booking() {
        final BookingDTO booking = BookingDTO.fromBooking(DomainFactory.createBooking());
        final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        final HttpEntity<?> request = new HttpEntity<>(booking, headers);
        this.localAddress.append("/book");

        System.out.println(this.restTemplate.postForEntity(this.localAddress.toString(), request, String.class));

        Assertions.assertDoesNotThrow(() -> Mockito.verify(this.categoryBookingService).book(this.actualBooking.capture()));
        Assertions.assertEquals(booking, this.actualBooking.getValue());

    }

}

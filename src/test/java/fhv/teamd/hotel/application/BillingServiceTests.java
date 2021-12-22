package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.BillDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.domain.Bill;
import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.BillId;
import fhv.teamd.hotel.domain.repositories.BillRepository;
import fhv.teamd.hotel.domain.repositories.SeasonRepository;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Field;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class BillingServiceTests {

    @MockBean
    private BillRepository billRepository;

    @MockBean
    private StayRepository stayRepository;

    @MockBean
    private SeasonRepository seasonRepository;

    @Autowired
    private BillingService billingService;

    @Test
    void given_intermediateBill_when_assignAllToRepresentative_then_works() throws ReflectiveOperationException {

        Bill b = Bill.createEmpty();

        Field idField = Bill.class.getDeclaredField("domainId");
        idField.setAccessible(true);
        idField.set(b, new BillId("dom-id-bill-111"));

        b.charge("kleine packung gummibärchen", 1, 3.0);
        b.charge("kleine packung gummibärchen", 1, 3.0);
        b.charge("leitungswasser", 2, 1.0);

        Mockito.when(this.billRepository.findById(b.billId())).thenReturn(Optional.of(b));

        final Address address = new Address("musterstrasse 1", "1234", "musterort", "musterland");

        RepresentativeDetails rep = new RepresentativeDetails(
                "max","muster","m@mail.com", address,"123456",
                "1111 1111 1111 1111", PaymentMethod.CreditCard);

        Assertions.assertDoesNotThrow(() -> this.billingService.assignPayments(
                "dom-id-bill-111",
                        List.of("leitungswasser"),
                rep));

        Assertions.assertFalse(b.intermediateEntries().stream().anyMatch(e -> e.description().equals("leitungswasser")));

        Assertions.assertEquals(rep, b.finalBills().get(0).billingAddress());

        Assertions.assertEquals("leitungswasser", b.finalBills().get(0).entries().get(0).description());

        Assertions.assertEquals(2, b.allEntries().size());

        Assertions.assertEquals(2.0, b.finalBills().get(0).calculateTotal());
        Assertions.assertEquals(6.0, b.totalOfIntermediateEntries());
        Assertions.assertEquals(8.0, b.calculateTotal());
    }


    @Test
    void given_Booking_when_get_intermediateBill_then_return_intermediateBill() {
        final Stay stay1 = DomainFactory.createStay();
        final Stay stay2 = DomainFactory.createStay();

        Mockito.when(this.stayRepository.findById(stay1.stayId())).thenReturn(Optional.of(stay1));
        Mockito.when(this.stayRepository.findById(stay2.stayId())).thenReturn(Optional.of(stay2));
        Mockito.when(this.seasonRepository.getSeasonFromMonth(any())).thenAnswer(invocation -> DomainFactory.getSeasonOf(invocation.getArgument(0, Month.class)));

        final AtomicReference<BillDTO> bill1 = new AtomicReference<>();
        final AtomicReference<BillDTO> bill2 = new AtomicReference<>();

        Assertions.assertDoesNotThrow(() -> bill1.set(this.billingService.getBill(stay1.stayId().toString())));

        Assertions.assertDoesNotThrow(() -> bill2.set(this.billingService.getBill(stay2.stayId().toString())));

        Assertions.assertThrows(InvalidIdException.class, () -> this.billingService.getBill(DomainFactory.createStayId().toString()));

        Assertions.assertEquals(BillDTO.fromBill(stay1.bill()), bill1.get());
        Assertions.assertEquals(BillDTO.fromBill(stay2.bill()), bill2.get());
    }

}

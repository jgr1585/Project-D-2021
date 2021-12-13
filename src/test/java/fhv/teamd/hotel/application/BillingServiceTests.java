package fhv.teamd.hotel.application;

import fhv.teamd.hotel.domain.Bill;
import fhv.teamd.hotel.domain.BillEntry;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.BillId;
import fhv.teamd.hotel.domain.repositories.BillRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Field;
import java.util.Optional;

@SpringBootTest
public class BillingServiceTests {

    @MockBean
    private BillRepository billRepository;

    @Autowired
    private BillingService billingService;

    @Test
    public void given_intermediateBill_when_assignAllToRepresentative_then_works() throws ReflectiveOperationException {

        Bill b = Bill.createEmpty();

        Field idField = Bill.class.getDeclaredField("domainId");
        idField.setAccessible(true);
        idField.set(b, new BillId("dom-id-bill-111"));

        b.charge("kleine packung gummibärchen", 2, 3.0);
        b.charge("leitungswasser", 2, 1.0);

        Mockito.when(this.billRepository.findById(b.billId())).thenReturn(Optional.of(b));

        final Address address = new Address("musterstrasse 1", "1234", "musterort", "musterland");

        RepresentativeDetails rep = new RepresentativeDetails(
                "max","muster","m@mail.com", address,"123456",
                "1111 1111 1111 1111", PaymentMethod.CreditCard);

        Assertions.assertDoesNotThrow(() -> this.billingService.assignPayments(
                "dom-id-bill-111",
                e -> e.description().equals("leitungswasser"),
                rep));

        Assertions.assertFalse(b.intermediateEntries().stream().anyMatch(e -> e.description().equals("leitungswasser")));

        Assertions.assertEquals(rep, b.finalBills().get(0).billingAddress());

        Assertions.assertEquals("leitungswasser", b.finalBills().get(0).entries().get(0).description());

        Assertions.assertEquals(2, b.allEntries().size());

        Assertions.assertEquals(2.0, b.finalBills().get(0).calculateTotal());
        Assertions.assertEquals(6.0, b.totalOfIntermediateEntries());
        Assertions.assertEquals(8.0, b.calculateTotal());
    }

}

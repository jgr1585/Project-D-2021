package fhv.teamd.hotel.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrganizationTests {

    private Organization org1;
    private Organization org2;

    @BeforeEach
    public void init() {
        this.org1 = DomainFactory.createOrganization();
        this.org2 = DomainFactory.createOrganization();
    }

    @SuppressWarnings({ "SimplifiableAssertion", "EqualsWithItself", "ConstantConditions", "EqualsBetweenInconvertibleTypes" })
    @Test
    public void testEquals() {
        Assertions.assertTrue(this.org1.equals(this.org1));
        Assertions.assertFalse(this.org1.equals(this.org2));
        Assertions.assertFalse(this.org1.equals(null));
        Assertions.assertFalse(this.org1.equals(""));
    }

}

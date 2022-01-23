package fhv.teamd.hotel.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryTests {

    private Category cat1;
    private Category cat2;


    @BeforeEach
    public void init() {
        this.cat1 = DomainFactory.createCategory();
        this.cat2 = DomainFactory.createCategory();
    }

    @SuppressWarnings({ "SimplifiableAssertion", "EqualsWithItself", "ConstantConditions", "EqualsBetweenInconvertibleTypes" })
    @Test
    public void testEquals() {
        Assertions.assertTrue(this.cat1.equals(this.cat1));
        Assertions.assertFalse(this.cat1.equals(this.cat2));
        Assertions.assertFalse(this.cat1.equals(null));
        Assertions.assertFalse(this.cat1.equals(""));
    }

}

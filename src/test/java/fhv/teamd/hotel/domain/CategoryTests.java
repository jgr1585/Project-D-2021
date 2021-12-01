package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.CategoryId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryTests {

    private Category cat1;
    private Category cat2;


    @SuppressWarnings("deprecation")
    @BeforeEach
    public void init() {
        this.cat1 = new Category(1L, new CategoryId("Category 1"), "Category 1", "Category 1", 20);
        this.cat2 = new Category(2L, new CategoryId("Category 2"),"Category 2", "Category 2", 30);
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

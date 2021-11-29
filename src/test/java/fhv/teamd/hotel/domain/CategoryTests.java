package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.CategoryId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
public class CategoryTests {

    private Category cat1;
    private Category cat2;


    @BeforeEach
    public void init() {
        this.cat1 = ReflectionUtils.newInstance(Category.class);
        this.cat2 = ReflectionUtils.newInstance(Category.class);

        ReflectionTestUtils.setField(this.cat1, "categoryId", new CategoryId("Cat 1"));
        ReflectionTestUtils.setField(this.cat2, "categoryId", new CategoryId("Cat 2"));

        ReflectionTestUtils.setField(this.cat1, "id", 1L);
        ReflectionTestUtils.setField(this.cat2, "id", 2L);

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

package fhv.teamd.hotel.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoomTests {

    private Room room1;
    private Room room2;

    @BeforeEach
    public void init() {
        this.room1 = ReflectionUtils.newInstance(Room.class);
        this.room2 = ReflectionUtils.newInstance(Room.class);
    }

    @SuppressWarnings({ "SimplifiableAssertion", "EqualsWithItself", "ConstantConditions", "EqualsBetweenInconvertibleTypes" })
    @Test
    public void testEquals() {
        Assertions.assertTrue(this.room1.equals(this.room1));
        Assertions.assertFalse(this.room1.equals(this.room2));
        Assertions.assertFalse(this.room1.equals(null));
        Assertions.assertFalse(this.room1.equals(""));
    }

}

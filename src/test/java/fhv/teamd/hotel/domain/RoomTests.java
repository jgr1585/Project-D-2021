package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.RoomId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
public class RoomTests {

    private Room room1;
    private Room room2;

    @BeforeEach
    public void init() {
        this.room1 = ReflectionUtils.newInstance(Room.class);
        this.room2 = ReflectionUtils.newInstance(Room.class);

        final RoomId roomId1 = new RoomId("Room 1");
        final RoomId roomId2 = new RoomId("Room 2");

        ReflectionTestUtils.setField(this.room1, "domainId", roomId1);
        ReflectionTestUtils.setField(this.room2, "domainId", roomId2);

        ReflectionTestUtils.setField(this.room1, "id", 1L);
        ReflectionTestUtils.setField(this.room2, "id", 2L);
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

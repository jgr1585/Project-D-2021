package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.StayDTO;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

@SpringBootTest
public class FrontDeskServiceTests {

    @Autowired
    private FrontDeskService frontDeskService;

    @MockBean
    private StayRepository stayRepository;

    @Test
    void given_emptyRepository_when_getAll_returnsEmpty() {
        Mockito.when(this.stayRepository.getActiveStays()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(0, this.frontDeskService.getActiveStays().size());
    }

}

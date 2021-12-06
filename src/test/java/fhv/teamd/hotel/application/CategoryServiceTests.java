package fhv.teamd.hotel.application;

import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;

@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void given_emptyAvailableCategories_when_getAllCategories_then_emptyList() {
        // given

        LocalDate from = LocalDate.now().plus(Period.ofWeeks(2));
        LocalDate until = from.plus(Period.ofWeeks(1));

        Mockito.when(this.categoryRepository.getAll()).thenReturn(Collections.emptyList());

        // when


        // then
        Assertions.assertEquals(0, this.categoryService.getAvailableCategories(from.atStartOfDay(), until.atStartOfDay()).size());

    }

}

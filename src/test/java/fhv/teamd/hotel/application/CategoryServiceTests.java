package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import fhv.teamd.hotel.domain.services.AvailabilityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private AvailabilityService availabilityService;

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

    @Test
    void given_nonemptyAvailableCategories_when_getAllCategories_then_Categroy_List() {
        // given
        List<Category> categories = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            categories.add(DomainFactory.createCategory());
        }

        Mockito.when(this.categoryRepository.getAll()).thenReturn(categories);

        LocalDateTime from = LocalDateTime.now().plus(Period.ofWeeks(2));
        LocalDateTime until = from.plus(Period.ofWeeks(1));

        Mockito.when(this.availabilityService.numberOfSuitableRooms(categories.get(0).categoryId(), from, until)).thenReturn(3);
        Mockito.when(this.availabilityService.numberOfSuitableRooms(categories.get(1).categoryId(), from, until)).thenReturn(2);
        Mockito.when(this.availabilityService.numberOfSuitableRooms(categories.get(2).categoryId(), from, until)).thenReturn(0);

        final List<AvailableCategoryDTO> expected = new LinkedList<>();
        expected.add(new AvailableCategoryDTO(categories.get(0).categoryId().toString(), categories.get(0).title(), 3));
        expected.add(new AvailableCategoryDTO(categories.get(1).categoryId().toString(), categories.get(1).title(), 2));
        expected.add(new AvailableCategoryDTO(categories.get(2).categoryId().toString(), categories.get(2).title(), 0));

        // when
        final List<AvailableCategoryDTO> actual = this.categoryService.getAvailableCategories(from, until);

        // then
        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    void given_CategoryList_when_getAll_then_return_all_Categories() {
        List<Category> categories = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            categories.add(DomainFactory.createCategory());
        }

        Mockito.when(this.categoryRepository.getAll()).thenReturn(categories);

        final List<CategoryDTO> expected = categories.stream().map(CategoryDTO::fromCategory).collect(Collectors.toList());
        final List<CategoryDTO> actual = this.categoryService.getAll();

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void given_Categories_when_findCategoryById_then_return_Category() {
        final Category cat1 = DomainFactory.createCategory();
        final Category cat2 = DomainFactory.createCategory();
        final CategoryId nonExistingCat = DomainFactory.createCategoryId();

        Mockito.when(this.categoryRepository.findById(cat1.categoryId())).thenReturn(Optional.of(cat1));
        Mockito.when(this.categoryRepository.findById(cat2.categoryId())).thenReturn(Optional.of(cat2));

        Assertions.assertEquals(CategoryDTO.fromCategory(cat1), this.categoryService.findCategoryById(cat1.categoryId().toString()).get());
        Assertions.assertEquals(CategoryDTO.fromCategory(cat2), this.categoryService.findCategoryById(cat2.categoryId().toString()).get());
        Assertions.assertEquals(Optional.empty(), this.categoryService.findCategoryById(nonExistingCat.toString()));


    }

}

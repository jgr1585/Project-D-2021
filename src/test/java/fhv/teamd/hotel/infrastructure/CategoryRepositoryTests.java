package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void given_category_when_getAll_return_all() {
        List<Category> expected = BaseRepositoryData.categories();
        List<Category> actual = this.categoryRepository.getAll();

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    void given_category_when_findById_return_categoryById() {
        List<Category> categories = BaseRepositoryData.categories();

        categories.forEach(category -> {
            @SuppressWarnings("OptionalGetWithoutIsPresent")
            Category actual = this.categoryRepository.findById(category.categoryId()).get();

            Assertions.assertEquals(category, actual);
        });
    }

    @Test
    void given_none_when_findById_return_EmptyOptional() {
        Assertions.assertEquals(Optional.empty(), this.categoryRepository.findById(DomainFactory.createCategoryId()));
    }
}

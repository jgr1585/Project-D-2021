package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.Season;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import fhv.teamd.hotel.domain.repositories.SeasonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SeasonRepository seasonRepository;

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

    @Test
    void given_cateogory_and_date_when_findSeasonPrice_return_PriceOfSeason() {
        List<Category> expCat = BaseRepositoryData.categories();
        List<Category> actCat = this.categoryRepository.getAll();

        for (Category category : actCat) {
            for (Season season : this.seasonRepository.getAll()) {
                final AtomicReference<Double> price = new AtomicReference<>();
                final AtomicReference<Category> expCat1 = new AtomicReference<>();

                for (Category category1 : expCat) {
                    if (category1 == category) {
                        expCat1.set(category1);
                    }
                }

                Assertions.assertDoesNotThrow(() -> price.set(category.pricePerNight(season)));
                Assertions.assertEquals(expCat1.get().pricePerNight(season), price.get());

            }
        }

    }
}

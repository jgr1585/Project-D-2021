package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.dto.BookableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAll() {

        List<Category> categories = this.categoryRepository.getAll();

        return categories
                .stream()
                .map(CategoryDTO::fromCategory)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<BookableCategoryDTO> getAvailableCategories(LocalDateTime from, LocalDateTime until) {
        List<BookableCategoryDTO> result = new ArrayList<>();

        for (Category cat : this.categoryRepository.getAll()) {

            // todo magic number xd
            int count = 99;
            result.add(new BookableCategoryDTO(
                    cat.categoryId().toString(),
                    cat.title(),
                    count));
        }

        return result;
    }

    @Override
    public boolean isAvailable(Map<String, Integer> categoryIdsAndAmounts, LocalDateTime from, LocalDateTime until) {

        // todo implement (out of sprint 1 scope)

        throw new NotYetImplementedException();
    }

    @Override
    public Optional<CategoryDTO> findCategoryById(String categoryId) {

        CategoryId id = new CategoryId(categoryId);

        Optional<Category> result = this.categoryRepository.findById(id);
        if(result.isEmpty()) {
            return Optional.empty();
        }

        Category category = result.get();

        return Optional.of(CategoryDTO.builder()
                .withId(category.categoryId().toString())
                .withTitle(category.title())
                .withDescription(category.description())
                .withPrice(category.pricePerNight())
                .build());
    }
}

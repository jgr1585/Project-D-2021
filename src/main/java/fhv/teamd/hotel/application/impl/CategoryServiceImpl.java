package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import fhv.teamd.hotel.domain.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AvailabilityService availabilityService;

    @Override
    public List<CategoryDTO> getAll() {

        List<Category> categories = this.categoryRepository.getAll();

        return categories
                .stream()
                .map(CategoryDTO::fromCategory)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<AvailableCategoryDTO> getAvailableCategories(LocalDateTime from, LocalDateTime until) {

        List<AvailableCategoryDTO> result = new ArrayList<>();

        // todo optimize with query

        this.categoryRepository.getAll().forEach(cat -> {
            int count = this.availabilityService.numberOfSuitableRooms(cat.categoryId(), from, until);
            result.add(new AvailableCategoryDTO(
                    cat.categoryId().toString(),
                    cat.title(),
                    count));
        });

        return result;
    }

    @Override
    public Optional<CategoryDTO> findCategoryById(String categoryId) {

        Optional<Category> result = this.categoryRepository.findById(new CategoryId(categoryId));

        return result.map(CategoryDTO::fromCategory);
    }
}

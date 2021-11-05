package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.dto.BookableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.application.dto.RequestedStayDTO;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import fhv.teamd.hotel.domain.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    // @Autowired
    // private AvailabilityService availabilityService;

    @Override
    public List<CategoryDTO> getAll() {

        List<Category> categories = this.categoryRepository.getAll();

        return categories.stream().map(cat ->
            CategoryDTO.builder()
                    .withId("FIXME")
                    .withTitle(cat.title())
                    .withDescription(cat.description())
                    .withPrice(cat.pricePerNight())
                    .build())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<BookableCategoryDTO> getAvailableCategories(LocalDateTime from, LocalDateTime until) {
        List<BookableCategoryDTO> result = new ArrayList<>();

        for(Category cat: this.categoryRepository.getAll()) {
            //int count = this.availabilityService.countAvailable(cat, from, until);
            int count = 99;
            result.add(new BookableCategoryDTO(cat.title(), count));
        }

        return result;
    }

    @Override
    public boolean isAvailable(RequestedStayDTO requestedStay) {
        // todo implement using availabilityService.checkAvailability()
        return true;
    }
}

package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.Season;

import java.util.Map;
import java.util.Objects;

public class CategoryDTO {

    private String id;
    private String title;
    private String description;
    private Map<Season, Double> price;

    public String id() {
        return this.id;
    }

    public String title() {
        return this.title;
    }

    public String description() {
        return this.description;
    }

    private CategoryDTO() {
    }

    public CategoryDTO(String id, String title, String description, Map<Season, Double> price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public static CategoryDTO fromCategory(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.id = category.categoryId() == null ? null : category.categoryId().toString();
        categoryDTO.description = category.description();
        categoryDTO.price = category.pricePerSeason();
        categoryDTO.title = category.title();

        return categoryDTO;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.title, that.title) && Objects.equals(this.description, that.description) && Objects.equals(this.price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.description, this.price);
    }
}

package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Category;

import java.util.Objects;

public class CategoryDTO {

    private String id;
    private String title;
    private String description;
    private double price;

    public String id() {
        return this.id;
    }

    public String title() {
        return this.title;
    }

    public String description() {
        return this.description;
    }

    public double price() {
        return this.price;
    }

    private CategoryDTO() {
    }

    public CategoryDTO(String id, String title, String description, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public static CategoryDTO fromCategory(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.id = category.categoryId() == null ? null : category.categoryId().toString();
        categoryDTO.description = category.description();
        categoryDTO.price = category.pricePerNight();
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
        return Double.compare(that.price, this.price) == 0 && Objects.equals(this.id, that.id) && Objects.equals(this.title, that.title) && Objects.equals(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.description, this.price);
    }
}

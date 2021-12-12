package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.CategoryId;

import java.util.Objects;

public class Category {
    private Long id;
    private CategoryId domainId;

    private String title;
    private String description;
    private double pricePerNight;

    protected Category() {
        // hibernate
    }

    //Only for Test
    @Deprecated
    public Category(Long id, CategoryId categoryId, String title, String description, double pricePerNight) {
        this.id = id;
        this.domainId = categoryId;
        this.title = title;
        this.description = description;
        this.pricePerNight = pricePerNight;
    }

    protected Long id() {
        return this.id;
    }

    public CategoryId categoryId() {
        return this.domainId;
    }

    public String title() {
        return this.title;
    }

    public String description() {
        return this.description;
    }

    public double pricePerNight() {
        return this.pricePerNight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Category category = (Category) o;
        return Objects.equals(this.id, category.id) && Objects.equals(this.domainId, category.domainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.domainId);
    }
}

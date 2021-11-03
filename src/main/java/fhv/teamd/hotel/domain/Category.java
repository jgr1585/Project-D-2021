package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.CategoryId;

public class Category {
    private Long id;
    private CategoryId categoryId;

    private String title;
    private String description;
    private double pricePerNight;

    private Category() { }

    public Category(String title, String description, double price) {
        this.title = title;
        this.description = description;
        this.pricePerNight = price;
    }

    protected Long id() {
        return this.id;
    }

    public String title() {
        return this.title;
    }

    public String description() {
        return this.description;
    }

    public double pricePerNight() { return this.pricePerNight; }
}

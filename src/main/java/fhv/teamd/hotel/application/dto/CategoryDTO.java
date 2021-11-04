package fhv.teamd.hotel.application.dto;

import java.util.Objects;

public class CategoryDTO {
    private Long id;
    private String title;
    private String description;
    private double price;

    public Long id() {
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

    public static class Builder {
        private CategoryDTO instance;

        private Builder() {
            this.instance = new CategoryDTO();
        }

        public Builder withId(Long id) {
            this.instance.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.instance.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.instance.description = description;
            return this;
        }

        public Builder withPrice(double price) {
            this.instance.price = price;
            return this;
        }

        public CategoryDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in CategoryDTO");
            Objects.requireNonNull(this.instance.title, "title must be set in CategoryDTO");
            Objects.requireNonNull(this.instance.description, "description must be set in CategoryDTO");
            Objects.requireNonNull(this.instance.price, "price must be set in CategoryDTO");

            return this.instance;
        }
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

package fhv.teamd.hotel.application.dto;

import java.util.Objects;

public class BookableCategoryDTO {

    private final String categoryName;
    private final int numberAvailable;

    public BookableCategoryDTO(String categoryName, int numberAvailable) {
        this.categoryName = categoryName;
        this.numberAvailable = numberAvailable;
    }

    public String categoryName() {
        return this.categoryName;
    }

    public int numberAvailable() {
        return this.numberAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookableCategoryDTO that = (BookableCategoryDTO) o;
        return numberAvailable == that.numberAvailable && Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, numberAvailable);
    }
}

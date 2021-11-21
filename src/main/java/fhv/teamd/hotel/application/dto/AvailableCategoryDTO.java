package fhv.teamd.hotel.application.dto;

import java.util.Objects;

public class AvailableCategoryDTO {

    private final String categoryId;
    private final String categoryName;
    private final int numberAvailable;

    public AvailableCategoryDTO(String categoryId, String categoryName, int numberAvailable) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.numberAvailable = numberAvailable;
    }

    public String categoryId() {
        return this.categoryId;
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
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final AvailableCategoryDTO that = (AvailableCategoryDTO) o;
        return this.numberAvailable == that.numberAvailable && Objects.equals(this.categoryId, that.categoryId) && Objects.equals(this.categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.categoryId, this.categoryName, this.numberAvailable);
    }
}

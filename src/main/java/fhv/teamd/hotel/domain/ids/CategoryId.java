package fhv.teamd.hotel.domain.ids;

import fhv.teamd.hotel.domain.Category;

public class CategoryId extends DomainId<Category> {
    private CategoryId() {
    }

    public CategoryId(String id) {
        super(id);
    }
}

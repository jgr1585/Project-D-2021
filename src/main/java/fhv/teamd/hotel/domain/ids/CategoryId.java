package fhv.teamd.hotel.domain.ids;

import fhv.teamd.hotel.domain.Category;

public class CategoryId extends DomainId<Category> {
    public CategoryId(String id) {
        super(id);
    }

    public CategoryId() {
        super("TEST123-UNINITIALIZED");
    }
}

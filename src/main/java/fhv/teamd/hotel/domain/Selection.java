package fhv.teamd.hotel.domain;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Objects;

/**
 * unused so far
 * can later be extended: booking by category(-ies) AND/OR specific room(s)
 */

public class Selection {

    private final Map<Category, Integer> categories;

    public Selection(Map<Category, Integer> categories) {

        if(categories == null || categories.isEmpty()) {
            throw new InvalidParameterException("may not be empty");
        }

        for(Integer count: categories.values()) {
            if(count <= 0) {
                throw new InvalidParameterException("number of rooms must be positive");
            }
        }

        this.categories = Map.copyOf(categories);
    }

    public Map<Category, Integer> categories() {
        return Map.copyOf(this.categories);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Selection that = (Selection) o;
        return Objects.equals(this.categories, that.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.categories);
    }
}

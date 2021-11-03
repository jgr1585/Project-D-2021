package fhv.teamd.hotel.domain.ids;

import java.util.Objects;

public abstract class DomainId<TEntity> {
    private final String id;

    public DomainId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        final DomainId<?> domainId = (DomainId<?>) o;
        return Objects.equals(this.id, domainId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}

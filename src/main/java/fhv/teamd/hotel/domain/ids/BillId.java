package fhv.teamd.hotel.domain.ids;

import java.util.Objects;

public class BillId {

    private String id;

    private BillId() {

    }

    public BillId(String id) {
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
        final BillId billId = (BillId) o;
        return Objects.equals(this.id, billId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return this.id;
    }
}

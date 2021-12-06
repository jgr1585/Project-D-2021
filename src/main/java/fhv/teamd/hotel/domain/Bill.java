package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.BillId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Bill {

    private Long id;
    private BillId billId;

    private final List<BillEntry> entries;

    private Bill() {
        this.entries = new ArrayList<>();
    }

    public static Bill createEmpty() {
        return new Bill();
    }

    public void charge(String reason, int amount, double unitPrice) {

        this.entries.add(new BillEntry(reason, LocalDateTime.now(), amount, unitPrice));
    }

    public List<BillEntry> entries() {

        return Collections.unmodifiableList(this.entries);
    }

    public double calculateTotal() {

        return this.entries
                .stream()
                .map(BillEntry::calculateSubTotal)
                .reduce(0.0, Double::sum);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Bill bill = (Bill) o;
        return Objects.equals(this.id, bill.id) && Objects.equals(this.billId, bill.billId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.billId);
    }


}

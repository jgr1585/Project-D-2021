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

    private List<BillLine> lines;

    private Bill() { }

    public static Bill createEmpty() {

        Bill bill = new Bill();
        bill.lines = new ArrayList<>();
        return bill;
    }

    public void charge(String reason, int amount, double unitPrice) {

        this.lines.add(new BillLine(reason, LocalDateTime.now(), amount, unitPrice));
    }

    public List<BillLine> lines() {

        return Collections.unmodifiableList(this.lines);
    }

    public double calculateTotal() {

        return this.lines
                .stream()
                .map(BillLine::calculateSubTotal)
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

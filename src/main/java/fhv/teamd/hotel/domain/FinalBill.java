package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.FinalBillId;

import java.util.List;
import java.util.Objects;

public class FinalBill {

    private long id;

    private FinalBillId domainId;

    private final RepresentativeDetails billingAddress;

    private final List<BillEntry> entries;


    public FinalBill(RepresentativeDetails billingAddress, List<BillEntry> entries) {

        this.billingAddress = billingAddress;
        this.entries = List.copyOf(entries);
    }

    public RepresentativeDetails billingAddress() {
        return this.billingAddress;
    }

    public List<BillEntry> entries() {

        // copy is unmodifiable
        return this.entries;
    }

    public double calculateTotal() {
        return this.entries.stream()
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
        final FinalBill finalBill = (FinalBill) o;

        boolean eq = Objects.equals(this.billingAddress, finalBill.billingAddress);

        if(this.entries != null && finalBill.entries != null) {
            eq &= this.entries.containsAll(finalBill.entries);
            eq &= finalBill.entries.containsAll(this.entries);
        }

        return eq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.billingAddress, this.entries);
    }


}

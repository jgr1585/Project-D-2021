package fhv.teamd.hotel.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class BillEntry {

    private final String description;
    private final LocalDateTime timestamp;
    private final int amount;
    private final double unitPrice;

    protected BillEntry() {
        this(null, null, 0, 0);
        // hibernate
    }

    public BillEntry(String description, LocalDateTime timestamp, int amount, double unitPrice) {
        this.description = description;
        this.timestamp = timestamp;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }

    public String description() {
        return this.description;
    }

    public LocalDateTime timestamp() {
        return this.timestamp;
    }

    public int amount() {
        return this.amount;
    }

    public double unitPrice() {
        return this.unitPrice;
    }

    public double calculateSubTotal() {

        return this.unitPrice * this.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final BillEntry billEntry = (BillEntry) o;
        return this.amount == billEntry.amount && Double.compare(billEntry.unitPrice, this.unitPrice) == 0 && Objects.equals(this.description, billEntry.description) && Objects.equals(this.timestamp, billEntry.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.description, this.timestamp, this.amount, this.unitPrice);
    }
}
package fhv.teamd.hotel.domain;

import java.time.LocalDateTime;

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
}
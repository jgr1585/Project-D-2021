package fhv.teamd.hotel.domain;

import java.time.LocalDateTime;

public class BillLine {

    private final String cause;
    private final LocalDateTime timestamp;
    private final int amount;
    private final double unitPrice;

    protected BillLine() {
        this(null, null, 0, 0);
        // hibernate
    }

    public BillLine(String cause, LocalDateTime timestamp, int amount, double unitPrice) {
        this.cause = cause;
        this.timestamp = timestamp;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }

    public String cause() {
        return this.cause;
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
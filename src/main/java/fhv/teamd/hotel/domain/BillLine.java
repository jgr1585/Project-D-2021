package fhv.teamd.hotel.domain;

import java.time.LocalDateTime;

public class BillLine {

    private final String cause;
    private final LocalDateTime timestamp;
    private final double amount;

    protected BillLine() {
        this(null, null, 0);
        // hibernate
    }

    public BillLine(String cause, LocalDateTime timestamp, double amount) {
        this.cause = cause;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public String cause() {
        return this.cause;
    }

    public LocalDateTime timestamp() {
        return this.timestamp;
    }

    public double amount() {
        return this.amount;
    }


}
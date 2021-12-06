package fhv.teamd.hotel.domain.contactInfo;

public enum PaymentMethod {
    Cash("Cash"),
    CreditCard("Credit Card");

    private final String displayValue;

    private PaymentMethod(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return this.displayValue;
    }
}

package fhv.teamd.hotel.domain.contactInfo;

public enum GuestType {
    Private("Private"),
    Organization("Organization");

    private final String displayValue;

    private GuestType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return this.displayValue;
    }
}

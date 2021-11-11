package fhv.teamd.hotel.domain;

import java.util.Objects;

public class GuestInfo {

    private final String name;
    private final Address address;

    public GuestInfo(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    private GuestInfo() {
        // hibernate
        this(null, null);
    }

    public String name() {
        return this.name;
    }

    public Address address() {
        return this.address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final GuestInfo guestInfo = (GuestInfo) o;
        return Objects.equals(this.name, guestInfo.name) && Objects.equals(this.address, guestInfo.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.address);
    }
}

package fhv.teamd.hotel.domain;

import java.util.Objects;

public class GuestInfo {

    private final String name;
    private final String address;

    public GuestInfo(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GuestInfo guestInfo = (GuestInfo) o;
        return Objects.equals(name, guestInfo.name) && Objects.equals(address, guestInfo.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}

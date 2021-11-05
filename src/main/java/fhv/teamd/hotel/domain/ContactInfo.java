package fhv.teamd.hotel.domain;

import java.util.Objects;

public class ContactInfo {

    private final String name;
    private final String email;
    private final String address;
    private final String phone;

    public ContactInfo(String name, String email, String address, String phone) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String name() {
        return this.name;
    }

    public String email() {
        return this.email;
    }

    public String address() {
        return this.address;
    }

    public String phone() {
        return this.phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ContactInfo that = (ContactInfo) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.email, that.email) && Objects.equals(this.address, that.address) && Objects.equals(this.phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.email, this.address, this.phone);
    }
}

package fhv.teamd.hotel.application.dto;

import java.util.Objects;

public class GuestDetailsDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String street;
    private String zip;
    private String city;
    private String country;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return this.id;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public String street() {
        return this.street;
    }

    public String zip() {
        return this.zip;
    }

    public String city() {
        return this.city;
    }

    public String country() {
        return this.country;
    }

    private GuestDetailsDTO() {
    }

    public static class Builder {
        private GuestDetailsDTO instance;

        private Builder() {
            this.instance = new GuestDetailsDTO();
        }

        public Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.instance.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.instance.lastName = lastName;
            return this;
        }

        public Builder withStreet(String street) {
            this.instance.street = street;
            return this;
        }

        public Builder withZip(String zip) {
            this.instance.zip = zip;
            return this;
        }

        public Builder withCity(String city) {
            this.instance.city = city;
            return this;
        }

        public Builder withCountry(String country) {
            this.instance.country = country;
            return this;
        }

        public GuestDetailsDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.firstName, "firstName must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.lastName, "lastName must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.street, "street must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.zip, "zip must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.city, "city must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.country, "country must be set in PersonalDetailsDTO");

            return this.instance;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final GuestDetailsDTO that = (GuestDetailsDTO) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.firstName, that.firstName) && Objects.equals(this.lastName, that.lastName) && Objects.equals(this.street, that.street) && Objects.equals(this.zip, that.zip) && Objects.equals(this.city, that.city) && Objects.equals(this.country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.street, this.zip, this.city, this.country);
    }
}

package fhv.teamd.hotel.application.dto;

import java.util.Objects;

public class RepresentativeDetailsDTO {

    private String firstName;
    private String lastName;
    private String street;
    private String zip;
    private String city;
    private String country;
    private String phone;
    private String email;

    public static Builder builder() {
        return new Builder();
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

    public String phone() {
        return this.phone;
    }

    public String email() {
        return this.email;
    }

    private RepresentativeDetailsDTO() {
    }

    public static class Builder {
        private RepresentativeDetailsDTO instance;

        private Builder() {
            this.instance = new RepresentativeDetailsDTO();
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

        public Builder withEmail(String email) {
            this.instance.email = email;
            return this;
        }

        public Builder withPhone(String phone) {
            this.instance.phone = phone;
            return this;
        }

        public RepresentativeDetailsDTO build() {
            Objects.requireNonNull(this.instance.firstName, "firstName must be set in RepresentativeDetailsDTO");
            Objects.requireNonNull(this.instance.lastName, "lastName must be set in RepresentativeDetailsDTO");
            Objects.requireNonNull(this.instance.street, "street must be set in RepresentativeDetailsDTO");
            Objects.requireNonNull(this.instance.zip, "zip must be set in RepresentativeDetailsDTO");
            Objects.requireNonNull(this.instance.city, "city must be set in RepresentativeDetailsDTO");
            Objects.requireNonNull(this.instance.country, "country must be set in RepresentativeDetailsDTO");
            Objects.requireNonNull(this.instance.email, "email must be set in RepresentativeDetailsDTO");
            Objects.requireNonNull(this.instance.phone, "phone must be set in RepresentativeDetailsDTO");

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
        final RepresentativeDetailsDTO that = (RepresentativeDetailsDTO) o;
        return Objects.equals(this.firstName, that.firstName) && Objects.equals(this.lastName, that.lastName) && Objects.equals(this.street, that.street) && Objects.equals(this.zip, that.zip) && Objects.equals(this.city, that.city) && Objects.equals(this.country, that.country) && Objects.equals(this.phone, that.phone) && Objects.equals(this.email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.street, this.zip, this.city, this.country, this.phone, this.email);
    }
}

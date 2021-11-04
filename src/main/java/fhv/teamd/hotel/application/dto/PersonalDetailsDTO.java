package fhv.teamd.hotel.application.dto;

import java.util.Objects;

public class PersonalDetailsDTO {
    private Long id;
    private String guestFirstName;
    private String guestLastName;
    private String guestStreet;
    private String guestZip;
    private String guestCity;
    private String guestCountry;

    private String representativeFirstName;
    private String representativeLastName;
    private String representativeStreet;
    private String representativeZip;
    private String representativeCity;
    private String representativeCountry;
    private String representativePhone;
    private String representativeEmail;

    public static Builder builder() {
        return new Builder();
    }

    public Long id() {
        return this.id;
    }

    public String guestFirstName() {
        return this.guestFirstName;
    }

    public String guestLastName() {
        return this.guestLastName;
    }

    public String guestStreet() {
        return this.guestStreet;
    }

    public String guestZip() {
        return this.guestZip;
    }

    public String guestCity() {
        return this.guestCity;
    }

    public String guestCountry() {
        return this.guestCountry;
    }

    public String representativeFirstName() {
        return this.representativeFirstName;
    }

    public String representativeLastName() {
        return this.representativeLastName;
    }

    public String representativeStreet() {
        return this.representativeStreet;
    }

    public String representativeZip() {
        return this.representativeZip;
    }

    public String representativeCity() {
        return this.representativeCity;
    }

    public String representativeCountry() {
        return this.representativeCountry;
    }

    public String representativePhone() {
        return this.representativePhone;
    }

    public String representativeEmail() {
        return this.representativeEmail;
    }

    private PersonalDetailsDTO() {
    }

    public static class Builder {
        private PersonalDetailsDTO instance;

        private Builder() {
            this.instance = new PersonalDetailsDTO();
        }

        public Builder withId(Long id) {
            this.instance.id = id;
            return this;
        }

        public Builder withGuestFirstName(String guestFirstName) {
            this.instance.guestFirstName = guestFirstName;
            return this;
        }

        public Builder withGuestLastName(String guestLastName) {
            this.instance.guestLastName = guestLastName;
            return this;
        }

        public Builder withGuestStreet(String guestStreet) {
            this.instance.guestStreet = guestStreet;
            return this;
        }

        public Builder withGuestZip(String guestZip) {
            this.instance.guestZip = guestZip;
            return this;
        }

        public Builder withGuestCity(String guestCity) {
            this.instance.guestCity = guestCity;
            return this;
        }

        public Builder withGuestCountry(String guestCountry) {
            this.instance.guestCountry = guestCountry;
            return this;
        }

        public Builder withRepresentativeFirstName(String representativeFirstName) {
            this.instance.representativeFirstName = representativeFirstName;
            return this;
        }

        public Builder withRepresentativeLastName(String representativeLastName) {
            this.instance.representativeLastName = representativeLastName;
            return this;
        }

        public Builder withRepresentativeStreet(String representativeStreet) {
            this.instance.representativeStreet = representativeStreet;
            return this;
        }

        public Builder withRepresentativeZip(String representativeZip) {
            this.instance.representativeZip = representativeZip;
            return this;
        }

        public Builder withRepresentativeCity(String representativeCity) {
            this.instance.representativeCity = representativeCity;
            return this;
        }

        public Builder withRepresentativeCountry(String guestCountry) {
            this.instance.representativeCountry = guestCountry;
            return this;
        }

        public Builder withRepresentativeEmail(String representativeEmail) {
            this.instance.representativeEmail = representativeEmail;
            return this;
        }

        public Builder withRepresentativePhone(String representativePhone) {
            this.instance.representativePhone = representativePhone;
            return this;
        }

        public PersonalDetailsDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestFirstName, "guestFirstName must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestLastName, "guestLastName must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestStreet, "guestStreet must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestZip, "guestZip must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestCity, "guestCity must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestCountry, "guestCountry must be set in PersonalDetailsDTO");

            Objects.requireNonNull(this.instance.representativeFirstName, "representativeFirstName must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.representativeLastName, "representativeLastName must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.representativeStreet, "representativeStreet must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.representativeZip, "representativeZip must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.representativeCity, "representativeCity must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.representativeCountry, "representativeCountry must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.representativeEmail, "representativeEmail must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.representativePhone, "representativePhone must be set in PersonalDetailsDTO");

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
        final PersonalDetailsDTO that = (PersonalDetailsDTO) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.guestFirstName, that.guestFirstName) && Objects.equals(this.guestLastName, that.guestLastName) && Objects.equals(this.guestStreet, that.guestStreet) && Objects.equals(this.guestZip, that.guestZip) && Objects.equals(this.guestCity, that.guestCity) && Objects.equals(this.guestCountry, that.guestCountry) && Objects.equals(this.representativeFirstName, that.representativeFirstName) && Objects.equals(this.representativeLastName, that.representativeLastName) && Objects.equals(this.representativeStreet, that.representativeStreet) && Objects.equals(this.representativeZip, that.representativeZip) && Objects.equals(this.representativeCity, that.representativeCity) && Objects.equals(this.representativeCountry, that.representativeCountry) && Objects.equals(this.representativePhone, that.representativePhone) && Objects.equals(this.representativeEmail, that.representativeEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.guestFirstName, this.guestLastName, this.guestStreet, this.guestZip, this.guestCity, this.guestCountry, this.representativeFirstName, this.representativeLastName, this.representativeStreet, this.representativeZip, this.representativeCity, this.representativeCountry, this.representativePhone, this.representativeEmail);
    }
}

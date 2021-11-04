package fhv.teamd.hotel.application.dto;

import java.util.Objects;

public class RepresentativeDetailsDTO {
    private Long id;
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

    private RepresentativeDetailsDTO() {
    }

    public static class Builder {
        private RepresentativeDetailsDTO instance;

        private Builder() {
            this.instance = new RepresentativeDetailsDTO();
        }

        public Builder withId(Long id) {
            this.instance.id = id;
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

        public RepresentativeDetailsDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in RepresentativeDetailsDTO");
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


}

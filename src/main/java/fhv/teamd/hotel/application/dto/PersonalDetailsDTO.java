package fhv.teamd.hotel.application.dto;

import java.util.Objects;

public class PersonalDetailsDTO {
    private Long id;
    private String guestFirstName;
    private String guestLastName;
    private String guestStreet;
    private int guestZip;
    private String guestCity;
    private String guestCountry;

    private String representativeFirstName;
    private String representativeLastName;
    private String representativeStreet;
    private int representativeZip;
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

    public int guestZip() {
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

    public int representativeZip() {
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

        public Builder withGuestZip(int guestZip) {
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

        public Builder withRepresentativeZip(int representativeZip) {
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

            return this.instance;
        }
    }

}

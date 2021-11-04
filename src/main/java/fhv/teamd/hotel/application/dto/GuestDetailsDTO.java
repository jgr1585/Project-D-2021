package fhv.teamd.hotel.application.dto;

import java.util.Objects;

public class GuestDetailsDTO {
    private Long id;
    private String guestFirstName;
    private String guestLastName;
    private String guestStreet;
    private String guestZip;
    private String guestCity;
    private String guestCountry;

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

    private GuestDetailsDTO() {
    }

    public static class Builder {
        private GuestDetailsDTO instance;

        private Builder() {
            this.instance = new GuestDetailsDTO();
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

        public GuestDetailsDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestFirstName, "guestFirstName must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestLastName, "guestLastName must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestStreet, "guestStreet must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestZip, "guestZip must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestCity, "guestCity must be set in PersonalDetailsDTO");
            Objects.requireNonNull(this.instance.guestCountry, "guestCountry must be set in PersonalDetailsDTO");

            return this.instance;
        }
    }


    // TODO: equals & hashCode

}

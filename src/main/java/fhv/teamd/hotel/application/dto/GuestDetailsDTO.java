package fhv.teamd.hotel.application.dto;

import java.util.Objects;

public class GuestDetailsDTO {

    private String id;
    private String guestFirstName;
    private String guestLastName;
    private String guestStreet;
    private String guestZip;
    private String guestCity;
    private String guestCountry;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
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

        public Builder withId(String id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final GuestDetailsDTO that = (GuestDetailsDTO) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.guestFirstName, that.guestFirstName) && Objects.equals(this.guestLastName, that.guestLastName) && Objects.equals(this.guestStreet, that.guestStreet) && Objects.equals(this.guestZip, that.guestZip) && Objects.equals(this.guestCity, that.guestCity) && Objects.equals(this.guestCountry, that.guestCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.guestFirstName, this.guestLastName, this.guestStreet, this.guestZip, this.guestCity, this.guestCountry);
    }
}

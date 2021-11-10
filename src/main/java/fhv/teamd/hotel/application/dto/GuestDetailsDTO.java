package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.GuestInfo;

import java.util.Objects;

public class GuestDetailsDTO {

    private String firstName;
    private String lastName;
    private String street;
    private String zip;
    private String city;
    private String country;

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

    public GuestDetailsDTO(String firstName, String lastName, String street, String zip, String city, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.country = country;
    }

    public static GuestDetailsDTO fromGuestDetails(GuestInfo guestInfo) {
        GuestDetailsDTO guestDetailsDTO = new GuestDetailsDTO();

//        guestDetailsDTO.city = guestInfo.


        return guestDetailsDTO;
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
        return Objects.equals(this.firstName, that.firstName) && Objects.equals(this.lastName, that.lastName) && Objects.equals(this.street, that.street) && Objects.equals(this.zip, that.zip) && Objects.equals(this.city, that.city) && Objects.equals(this.country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.street, this.zip, this.city, this.country);
    }
}

package fhv.teamd.hotel.view.forms.subForms;

import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.GuestType;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;

public class PersonalDetailsForm {
    private boolean isSamePerson;
    private GuestType guestType;

    private String organizationName;
    private int discount;

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
    private String representativeMail;
    private String representativePhone;

    private String representativeCreditCardNumber;
    private PaymentMethod representativePaymentMethod;

    // required by spring/thymeleaf
    public PersonalDetailsForm() {
    }

    public PersonalDetailsForm(boolean isSamePerson, GuestType guestType, String organizationName, int discount, String guestFirstName, String guestLastName,
                               String guestStreet, String guestZip, String guestCity, String guestCountry, String representativeFirstName, String representativeLastName,
                               String representativeStreet, String representativeZip, String representativeCity, String representativeCountry, String representativeMail,
                               String representativePhone, String representativeCreditCardNumber, PaymentMethod representativePaymentMethod) {
        this.isSamePerson = isSamePerson;
        this.guestType = guestType;

        this.organizationName = organizationName;
        this.discount = discount;

        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.guestStreet = guestStreet;
        this.guestZip = guestZip;
        this.guestCity = guestCity;
        this.guestCountry = guestCountry;

        this.representativeFirstName = representativeFirstName;
        this.representativeLastName = representativeLastName;
        this.representativeStreet = representativeStreet;
        this.representativeZip = representativeZip;
        this.representativeCity = representativeCity;
        this.representativeCountry = representativeCountry;
        this.representativeMail = representativeMail;
        this.representativePhone = representativePhone;
        this.representativeCreditCardNumber = representativeCreditCardNumber;
        this.representativePaymentMethod = representativePaymentMethod;
    }

    public boolean isSamePerson() {
        return this.isSamePerson;
    }

    public void setSamePerson(boolean isSamePerson) {
        this.isSamePerson = isSamePerson;
    }

    public GuestType getGuestType() {
        return this.guestType;
    }

    public void setGuestType(GuestType guestType) {
        this.guestType = guestType;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getDiscount() {
        return this.discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getGuestFirstName() {
        return this.guestFirstName;
    }

    public void setGuestFirstName(String guestFirstName) {
        this.guestFirstName = guestFirstName;
    }

    public String getGuestLastName() {
        return this.guestLastName;
    }

    public void setGuestLastName(String guestLastName) {
        this.guestLastName = guestLastName;
    }

    public String getGuestStreet() {
        return this.guestStreet;
    }

    public void setGuestStreet(String guestStreet) {
        this.guestStreet = guestStreet;
    }

    public String getGuestZip() {
        return this.guestZip;
    }

    public void setGuestZip(String guestZip) {
        this.guestZip = guestZip;
    }

    public String getGuestCity() {
        return this.guestCity;
    }

    public void setGuestCity(String guestCity) {
        this.guestCity = guestCity;
    }

    public String getGuestCountry() {
        return this.guestCountry;
    }

    public void setGuestCountry(String guestCountry) {
        this.guestCountry = guestCountry;
    }

    public String getRepresentativeFirstName() {
        return this.representativeFirstName;
    }

    public void setRepresentativeFirstName(String representativeFirstName) {
        this.representativeFirstName = representativeFirstName;
    }

    public String getRepresentativeLastName() {
        return this.representativeLastName;
    }

    public void setRepresentativeLastName(String representativeLastName) {
        this.representativeLastName = representativeLastName;
    }

    public String getRepresentativeStreet() {
        return this.representativeStreet;
    }

    public void setRepresentativeStreet(String representativeStreet) {
        this.representativeStreet = representativeStreet;
    }

    public String getRepresentativeZip() {
        return this.representativeZip;
    }

    public void setRepresentativeZip(String representativeZip) {
        this.representativeZip = representativeZip;
    }

    public String getRepresentativeCity() {
        return this.representativeCity;
    }

    public void setRepresentativeCity(String representativeCity) {
        this.representativeCity = representativeCity;
    }

    public String getRepresentativeCountry() {
        return this.representativeCountry;
    }

    public void setRepresentativeCountry(String representativeCountry) {
        this.representativeCountry = representativeCountry;
    }

    public String getRepresentativeMail() {
        return this.representativeMail;
    }

    public void setRepresentativeMail(String representativeMail) {
        this.representativeMail = representativeMail;
    }

    public String getRepresentativePhone() {
        return this.representativePhone;
    }

    public void setRepresentativePhone(String representativePhone) {
        this.representativePhone = representativePhone;
    }

    public String getRepresentativeCreditCardNumber() {
        return this.representativeCreditCardNumber;
    }

    public void setRepresentativeCreditCardNumber(String representativeCreditCardNumber) {
        this.representativeCreditCardNumber = representativeCreditCardNumber;
    }

    public PaymentMethod getRepresentativePaymentMethod() {
        return this.representativePaymentMethod;
    }

    public void setRepresentativePaymentMethod(PaymentMethod representativePaymentMethod) {
        this.representativePaymentMethod = representativePaymentMethod;
    }
}
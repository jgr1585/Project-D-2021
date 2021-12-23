package fhv.teamd.hotel.view.forms.subForms;

import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;

public class BillAssignmentForm {

    private String firstName;
    private String lastName;
    private String street;
    private String zip;
    private String city;
    private String country;
    private String mail;
    private String phone;

    private String creditCardNumber;
    private PaymentMethod paymentMethod;

    // hibernate
    public BillAssignmentForm() {
    }

    public BillAssignmentForm(String firstName, String lastName,
                              String street, String zip, String city, String country,
                              String mail, String phone, String creditCardNumber, PaymentMethod paymentMethod) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.country = country;
        this.mail = mail;
        this.phone = phone;
        this.creditCardNumber = creditCardNumber;
        this.paymentMethod = paymentMethod;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreditCardNumber() {
        return this.creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

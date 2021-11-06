package fhv.teamd.hotel.view.forms;

import fhv.teamd.hotel.application.dto.BookableCategoryDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Map;

public class ExperimentalBookingForm {

    public static class CategoryField {
        private BookableCategoryDTO category;
        private int selectedAmount;

        public CategoryField(BookableCategoryDTO category, int selectedAmount) {
            this.category = category;
            this.selectedAmount = selectedAmount;
        }

        public CategoryField() {
            // thymeleaf
        }

        public BookableCategoryDTO getCategory() {
            return this.category;
        }

        public void setCategory(BookableCategoryDTO category) {
            this.category = category;
        }

        public int getSelectedAmount() {
            return this.selectedAmount;
        }

        public void setSelectedAmount(int selectedAmount) {
            this.selectedAmount = selectedAmount;
        }
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate from;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate until;

    // need a string index (-> id) for thymeleaf.
    private Map<String, ExperimentalBookingForm.CategoryField> categorySelection;


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

    public ExperimentalBookingForm() {
        // required by spring/thymeleaf
    }

    public LocalDate getFromDate() {
        return this.from;
    }

    public String getFrom() {
        return this.from.toString();
    }

    public void setFromDate(LocalDate from) {
        this.from = from;
    }

    public void setFrom(String from) {
        this.setFromDate(LocalDate.parse(from));
    }

    public LocalDate getUntilDate() {
        return this.until;
    }

    public String getUntil() {
        return this.until.toString();
    }

    public void setUntilDate (LocalDate until) {
        this.until = until;
    }

    public void setUntil(String until) {
        this.setUntilDate(LocalDate.parse(until));
    }


    public Map<String, ExperimentalBookingForm.CategoryField> getCategorySelection() {
        return this.categorySelection;
    }

    public void setCategorySelection(Map<String, ExperimentalBookingForm.CategoryField> categorySelection) {
        this.categorySelection = categorySelection;
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

}

package fhv.teamd.hotel.view.forms.subForms;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class BookingListForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fromDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime untilDate;

    private String representativeLastName;

    // required by spring/thymeleaf
    public BookingListForm() {
    }

    public BookingListForm(LocalDateTime fromDate, LocalDateTime untilDate, String representativeLastName) {
        this.fromDate = fromDate;
        this.untilDate = untilDate;
        this.representativeLastName = representativeLastName;
    }

    public LocalDateTime getFromDate() {return this.fromDate;}

    public LocalDateTime getUntilDate() {return this.untilDate;}

    public String getRepresentativeLastName() {return this.representativeLastName;}

    public void setFromDate(LocalDateTime fromDate) {this.fromDate = fromDate;}

    public void setUntilDate(LocalDateTime untilDate) {this.untilDate = untilDate;}

    public void setRepresentativeLastName(String representativeLastName) {this.representativeLastName = representativeLastName;}
}


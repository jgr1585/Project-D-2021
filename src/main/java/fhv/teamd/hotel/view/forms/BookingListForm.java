package fhv.teamd.hotel.view.forms;

import java.time.LocalDateTime;

public class BookingListForm {

    private LocalDateTime fromDate;
    private LocalDateTime untilDate;
    private String representativeLastName;

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


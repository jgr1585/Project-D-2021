package fhv.teamd.hotel.view.forms;

import fhv.teamd.hotel.view.forms.subForms.BookingListForm;
import fhv.teamd.hotel.view.forms.subForms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.subForms.PersonalDetailsForm;
import fhv.teamd.hotel.view.forms.subForms.RoomAssignmentForm;

public class GlobalForm {
    private BookingListForm bookingListForm;
    private ChooseCategoriesForm chooseCategoriesForm;
    private PersonalDetailsForm personalDetailsForm;
    private RoomAssignmentForm roomAssignmentForm;

    // required by spring/thymeleaf
    public GlobalForm() {
    }

    public GlobalForm(BookingListForm bookingListForm, ChooseCategoriesForm chooseCategoriesForm, PersonalDetailsForm personalDetailsForm, RoomAssignmentForm roomAssignmentForm) {
        this.bookingListForm = bookingListForm;
        this.chooseCategoriesForm = chooseCategoriesForm;
        this.personalDetailsForm = personalDetailsForm;
        this.roomAssignmentForm = roomAssignmentForm;
    }

    public BookingListForm getBookingListForm() {
        return this.bookingListForm;
    }

    public void setBookingListForm(BookingListForm bookingListForm) {
        this.bookingListForm = bookingListForm;
    }

    public ChooseCategoriesForm getChooseCategoriesForm() {
        return this.chooseCategoriesForm;
    }

    public void setChooseCategoriesForm(ChooseCategoriesForm chooseCategoriesForm) {
        this.chooseCategoriesForm = chooseCategoriesForm;
    }

    public PersonalDetailsForm getPersonalDetailsForm() {
        return this.personalDetailsForm;
    }

    public void setPersonalDetailsForm(PersonalDetailsForm personalDetailsForm) {
        this.personalDetailsForm = personalDetailsForm;
    }

    public RoomAssignmentForm getRoomAssignmentForm() {
        return this.roomAssignmentForm;
    }

    public void setRoomAssignmentForm(RoomAssignmentForm roomAssignmentForm) {
        this.roomAssignmentForm = roomAssignmentForm;
    }
}

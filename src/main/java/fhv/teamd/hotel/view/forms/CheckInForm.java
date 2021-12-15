package fhv.teamd.hotel.view.forms;

import fhv.teamd.hotel.view.forms.subForms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.subForms.PersonalDetailsForm;
import fhv.teamd.hotel.view.forms.subForms.RoomAssignmentForm;

public class CheckInForm {

    private String bookingId;

    private ChooseCategoriesForm chooseCategoriesForm = new ChooseCategoriesForm();
    private PersonalDetailsForm personalDetailsForm = new PersonalDetailsForm();
    private RoomAssignmentForm roomAssignmentForm = new RoomAssignmentForm();

    // required by spring/thymeleaf
    public CheckInForm() {
    }

    public CheckInForm(String bookingId,
                       ChooseCategoriesForm chooseCategoriesForm,
                       PersonalDetailsForm personalDetailsForm,
                       RoomAssignmentForm roomAssignmentForm) {

        this.bookingId = bookingId;
        this.chooseCategoriesForm = chooseCategoriesForm;
        this.personalDetailsForm = personalDetailsForm;
        this.roomAssignmentForm = roomAssignmentForm;
    }

    public String getBookingId() {
        return this.bookingId;
    }

    public ChooseCategoriesForm getChooseCategoriesForm() {
        return this.chooseCategoriesForm;
    }

    public PersonalDetailsForm getPersonalDetailsForm() {
        return this.personalDetailsForm;
    }

    public RoomAssignmentForm getRoomAssignmentForm() {
        return this.roomAssignmentForm;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setChooseCategoriesForm(ChooseCategoriesForm chooseCategoriesForm) {
        this.chooseCategoriesForm = chooseCategoriesForm;
    }

    public void setPersonalDetailsForm(PersonalDetailsForm personalDetailsForm) {
        this.personalDetailsForm = personalDetailsForm;
    }

    public void setRoomAssignmentForm(RoomAssignmentForm roomAssignmentForm) {
        this.roomAssignmentForm = roomAssignmentForm;
    }
}

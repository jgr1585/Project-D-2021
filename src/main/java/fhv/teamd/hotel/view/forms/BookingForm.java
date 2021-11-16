package fhv.teamd.hotel.view.forms;

import fhv.teamd.hotel.view.forms.subForms.ChooseCategoriesForm;
import fhv.teamd.hotel.view.forms.subForms.PersonalDetailsForm;

public class BookingForm {
    private ChooseCategoriesForm chooseCategoriesForm;
    private PersonalDetailsForm personalDetailsForm;

    // required by spring/thymeleaf
    public BookingForm() {
    }

    public BookingForm(ChooseCategoriesForm chooseCategoriesForm, PersonalDetailsForm personalDetailsForm) {
        this.chooseCategoriesForm = chooseCategoriesForm;
        this.personalDetailsForm = personalDetailsForm;
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
}

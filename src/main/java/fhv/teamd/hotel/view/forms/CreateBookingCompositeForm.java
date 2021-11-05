package fhv.teamd.hotel.view.forms;

public class CreateBookingCompositeForm {

    private ChooseCategoriesForm chooseCategoriesForm;
    private CustomerDetailsForm customerDetailsForm;

    public ChooseCategoriesForm getChooseCategoriesForm() {
        return this.chooseCategoriesForm;
    }

    public void setChooseCategoriesForm(ChooseCategoriesForm chooseCategoriesForm) {
        this.chooseCategoriesForm = chooseCategoriesForm;
    }

    public CustomerDetailsForm getCustomerDetailsForm() {
        return this.customerDetailsForm;
    }

    public void setCustomerDetailsForm(CustomerDetailsForm customerDetailsForm) {
        this.customerDetailsForm = customerDetailsForm;
    }
}

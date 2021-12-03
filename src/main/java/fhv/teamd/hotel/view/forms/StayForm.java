package fhv.teamd.hotel.view.forms;

import fhv.teamd.hotel.view.forms.subForms.InvoiceForm;

public class StayForm {

    private InvoiceForm invoiceForm = new InvoiceForm();

    // required by spring/thymeleaf
    public StayForm() {
    }

    public StayForm(InvoiceForm invoiceForm) {
        this.invoiceForm = invoiceForm;
    }

    public InvoiceForm getInvoiceForm() {
        return this.invoiceForm;
    }

    public void setInvoiceForm(InvoiceForm invoiceForm) {
        this.invoiceForm = invoiceForm;
    }
}

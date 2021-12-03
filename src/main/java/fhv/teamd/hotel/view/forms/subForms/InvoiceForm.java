package fhv.teamd.hotel.view.forms.subForms;

import java.util.Map;

public class InvoiceForm {

    private Map<String, Integer> invoiceEntries;

    // required by spring/thymeleaf
    public InvoiceForm() {
    }

    public InvoiceForm(Map<String, Integer> invoiceEntries) {
        this.invoiceEntries = invoiceEntries;
    }

    public Map<String, Integer> getInvoiceEntries() {
        return this.invoiceEntries;
    }

    public void setInvoiceEntries(Map<String, Integer> invoiceEntries) {
        this.invoiceEntries = invoiceEntries;
    }
}

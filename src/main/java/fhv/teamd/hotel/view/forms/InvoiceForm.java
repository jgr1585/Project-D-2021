package fhv.teamd.hotel.view.forms;

import fhv.teamd.hotel.view.forms.subForms.BillAssignmentForm;

public class InvoiceForm {

    private String stayId;
    private BillAssignmentForm billAssignmentForm = new BillAssignmentForm();

    // required by spring/thymeleaf
    public InvoiceForm() {
    }

    public InvoiceForm(String stayId, BillAssignmentForm billAssignmentForm) {
        this.stayId = stayId;
        this.billAssignmentForm = billAssignmentForm;
    }

    public String getStayId() {
        return this.stayId;
    }

    public void setStayId(String stayId) {
        this.stayId = stayId;
    }

    public BillAssignmentForm getBillAssignmentForm() {
        return this.billAssignmentForm;
    }

    public void setBillAssignmentForm(BillAssignmentForm billAssignmentForm) {
        this.billAssignmentForm = billAssignmentForm;
    }
}

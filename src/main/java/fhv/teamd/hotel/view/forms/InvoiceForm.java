package fhv.teamd.hotel.view.forms;

import fhv.teamd.hotel.view.forms.subForms.BillAssignmentForm;

import java.util.ArrayList;
import java.util.List;

public class InvoiceForm {

    private String stayId;
    private BillAssignmentForm billAssignmentForm = new BillAssignmentForm();

    private List<Boolean> selectedBills;

    // required by spring/thymeleaf
    public InvoiceForm() {
        this.selectedBills = new ArrayList<>();
    }

    public InvoiceForm(String stayId, BillAssignmentForm billAssignmentForm, List<Boolean> selectedBills) {
        this.stayId = stayId;
        this.billAssignmentForm = billAssignmentForm;
        this.selectedBills = selectedBills;
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

    public List<Boolean> getSelectedBills() {
        return this.selectedBills;
    }

    public void setSelectedBills(List<Boolean> selectedBills) {
        this.selectedBills = selectedBills;
    }
}

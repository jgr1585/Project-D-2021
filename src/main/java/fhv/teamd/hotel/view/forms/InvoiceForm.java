package fhv.teamd.hotel.view.forms;

import fhv.teamd.hotel.view.forms.subForms.BillAssignmentForm;

import java.util.HashMap;
import java.util.Map;

public class InvoiceForm {

    private String stayId;
    private BillAssignmentForm billAssignmentForm = new BillAssignmentForm();

    private Map<Integer, Boolean> selectedBills;

    // required by spring/thymeleaf
    public InvoiceForm() {
        this.selectedBills = new HashMap<>();
    }

    public InvoiceForm(String stayId, BillAssignmentForm billAssignmentForm, Map<Integer, Boolean> selectedBills) {
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

    public Map<Integer, Boolean> getSelectedBills() {
        return this.selectedBills;
    }

    public void setSelectedBills(Map<Integer, Boolean> selectedBills) {
        this.selectedBills = selectedBills;
    }
}

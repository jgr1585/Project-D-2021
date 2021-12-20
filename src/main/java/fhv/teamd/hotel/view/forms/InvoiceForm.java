package fhv.teamd.hotel.view.forms;

import fhv.teamd.hotel.application.dto.BillEntryDTO;
import fhv.teamd.hotel.view.forms.subForms.BillAssignmentForm;

import java.util.ArrayList;
import java.util.List;

public class InvoiceForm {

    private String stayId;
    private BillAssignmentForm billAssignmentForm = new BillAssignmentForm();

    private List<Boolean> checkboxStates;
    private List<BillEntryDTO> selectedBillEntries;

    private double discount;
    private double subTotal;
    private double invoiceTotal;
    private double taxAmount;
    private double invoiceTotalTax;

    // required by spring/thymeleaf
    public InvoiceForm() {
        this.checkboxStates = new ArrayList<>();
        this.selectedBillEntries = new ArrayList<>();
        this.discount = 0;
        this.subTotal = 0;
        this.invoiceTotal = 0;
        this.taxAmount = 0;
        this.invoiceTotalTax = 0;
    }

    public InvoiceForm(String stayId, BillAssignmentForm billAssignmentForm, List<Boolean> selectedBills, List<BillEntryDTO> selectedBillEntries, double discount, double subTotal, double invoiceTotal, double tabAmount, double invoiceTotalTax) {
        this.stayId = stayId;
        this.billAssignmentForm = billAssignmentForm;
        this.checkboxStates = selectedBills;
        this.selectedBillEntries = selectedBillEntries;
        this.discount = discount;
        this.subTotal = subTotal;
        this.invoiceTotal = invoiceTotal;
        this.taxAmount = tabAmount;
        this.invoiceTotalTax = invoiceTotalTax;
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

    public List<Boolean> getCheckboxStates() {
        return this.checkboxStates;
    }

    public void setCheckboxStates(List<Boolean> checkboxStates) {
        this.checkboxStates = checkboxStates;
    }

    public List<BillEntryDTO> getSelectedBillEntries() {
        return this.selectedBillEntries;
    }

    public void setSelectedBillEntries(List<BillEntryDTO> selectedBillEntries) {
        this.selectedBillEntries = selectedBillEntries;
    }

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getSubTotal() {
        return this.subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getInvoiceTotal() {
        return this.invoiceTotal;
    }

    public void setInvoiceTotal(double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public double getTaxAmount() {
        return this.taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getInvoiceTotalTax() {
        return this.invoiceTotalTax;
    }

    public void setInvoiceTotalTax(double invoiceTotalTax) {
        this.invoiceTotalTax = invoiceTotalTax;
    }
}

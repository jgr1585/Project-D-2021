package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.BillEntry;

import java.util.Objects;

public class BillEntryDTO {

    private String description;
    private int amount;
    private double unitPrice;
    private double subTotal;

    private BillEntryDTO() { }

    public static BillEntryDTO fromEntry(BillEntry billEntry) {

        BillEntryDTO dto = new BillEntryDTO();

        dto.description = billEntry.description();
        dto.amount = billEntry.amount();
        dto.unitPrice = billEntry.unitPrice();
        dto.subTotal = billEntry.calculateSubTotal();

        return dto;
    }

    public String description() {
        return this.description;
    }

    public int amount() {
        return this.amount;
    }

    public double unitPrice() {
        return this.unitPrice;
    }

    public double subTotal() {
        return this.subTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final BillEntryDTO that = (BillEntryDTO) o;
        return this.amount == that.amount && Double.compare(that.unitPrice, this.unitPrice) == 0 && Double.compare(that.subTotal, this.subTotal) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.amount, this.unitPrice, this.subTotal);
    }



}

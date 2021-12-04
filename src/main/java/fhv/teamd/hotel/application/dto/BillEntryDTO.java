package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.BillLine;

import java.util.Objects;

public class BillEntryDTO {

    private int amount;
    private double unitPrice;
    private double subTotal;

    private BillEntryDTO() { }

    public static BillEntryDTO fromEntry(BillLine billLine) {

        BillEntryDTO dto = new BillEntryDTO();

        dto.amount = billLine.amount();
        dto.unitPrice = billLine.unitPrice();
        dto.subTotal = billLine.calculateSubTotal();

        return dto;
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

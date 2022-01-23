package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.FinalBill;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FinalBillDTO {

    private double total;
    private List<BillEntryDTO> entries;
    private RepresentativeDetails representative;

    private FinalBillDTO() { }

    public static FinalBillDTO fromFinalBill(FinalBill bill) {

        FinalBillDTO dto = new FinalBillDTO();

        dto.total = bill.calculateTotal();
        dto.entries = bill.entries()
                .stream()
                .map(BillEntryDTO::fromEntry)
                .collect(Collectors.toUnmodifiableList());
        dto.representative = bill.billingAddress();

        return dto;
    }

    public double total() {
        return this.total;
    }

    public List<BillEntryDTO> entries() {

        return Collections.unmodifiableList(this.entries);
    }

    public RepresentativeDetails representative() {

        return this.representative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FinalBillDTO billDTO = (FinalBillDTO) o;
        return Double.compare(billDTO.total, this.total) == 0 && Objects.equals(this.entries, billDTO.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.total, this.entries);
    }
}

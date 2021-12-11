package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Bill;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BillDTO {

    private double total;
    private List<BillEntryDTO> entries;


    private BillDTO() { }

    public static BillDTO fromBill(Bill bill) {

        BillDTO dto = new BillDTO();

        dto.total = bill.calculateTotal();
        dto.entries = bill.entries().stream().map(BillEntryDTO::fromEntry).collect(Collectors.toUnmodifiableList());

        return dto;
    }

    public double total() {
        return this.total;
    }

    public List<BillEntryDTO> entries() {

        return Collections.unmodifiableList(this.entries);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final BillDTO billDTO = (BillDTO) o;
        return Double.compare(billDTO.total, this.total) == 0 && Objects.equals(this.entries, billDTO.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.total, this.entries);
    }
}

package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Bill;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BillDTO {

    private String id;

    private double total;
    private List<BillEntryDTO> entries;

    private List<FinalBillDTO> finalBills;


    private BillDTO() { }

    public static BillDTO fromBill(Bill bill) {

        BillDTO dto = new BillDTO();

        dto.id = bill.billId().toString();

        dto.total = bill.calculateTotal();
        dto.entries = bill.intermediateEntries()
                .stream()
                .map(BillEntryDTO::fromEntry)
                .collect(Collectors.toUnmodifiableList());
        dto.finalBills = bill.finalBills()
                .stream()
                .map(FinalBillDTO::fromFinalBill)
                .collect(Collectors.toUnmodifiableList());

        return dto;
    }

    public String id() {
        return this.id;
    }

    public double total() {
        return this.total;
    }

    public List<BillEntryDTO> entries() {
        return Collections.unmodifiableList(this.entries);
    }

    public List<FinalBillDTO> finalBills() {
        return Collections.unmodifiableList(this.finalBills);
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

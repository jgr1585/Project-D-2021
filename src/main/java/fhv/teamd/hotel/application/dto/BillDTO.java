package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Bill;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BillDTO {

    private double total;
    private List<BillEntryDTO> entries;


    private BillDTO() { }

    public static BillDTO fromBill(Bill bill) {

        BillDTO dto = new BillDTO();

        dto.total = bill.calculateTotal();
        dto.entries = bill.lines().stream().map(BillEntryDTO::fromEntry).collect(Collectors.toUnmodifiableList());

        return dto;
    }

    public double total() {
        return this.total;
    }

    public List<BillEntryDTO> entries() {

        return Collections.unmodifiableList(this.entries);

    }
}

package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.BillId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bill {

    private Long id;
    private BillId billId;

    private List<BillLine> lines;

    public Bill() {
        this.lines = new ArrayList<>();
    }

    public void charge(String reason, double amount) {

        this.lines.add(new BillLine(reason, LocalDateTime.now(), amount));
    }

    public List<BillLine> lines() {

        return Collections.unmodifiableList(this.lines);
    }



}

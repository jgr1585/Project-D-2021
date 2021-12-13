package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.BillId;
import fhv.teamd.hotel.domain.ids.DomainId;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bill {

    private Long id;
    private BillId domainId;

    private final List<BillEntry> intermediateEntries;

    private final List<FinalBill> finalBills;

    private Bill() {

        this.intermediateEntries = new ArrayList<>();
        this.finalBills = new ArrayList<>();
    }

    public static Bill createEmpty() {
        return new Bill();
    }

    public BillId billId() {
        return this.domainId;
    }

    public void charge(String description, int amount, double unitPrice) {

        Optional<BillEntry> matchingEntry = this.intermediateEntries.stream()
                .filter(e -> e.description().equals(description) && e.unitPrice() == unitPrice)
                .findFirst();

        if(matchingEntry.isPresent()) {

            this.intermediateEntries.remove(matchingEntry.get());
            amount += matchingEntry.get().amount();
        }

        this.intermediateEntries.add(new BillEntry(description, LocalDateTime.now(), amount, unitPrice));
    }

    public List<BillEntry> intermediateEntries() {

        return Collections.unmodifiableList(this.intermediateEntries);
    }

    public List<BillEntry> allEntries() {

        Stream<BillEntry> finalEntries = this.finalBills.stream().flatMap(bill -> bill.entries().stream());
        Stream<BillEntry> allEntries = Stream.concat(finalEntries, this.intermediateEntries.stream());
        Stream<BillEntry> sortedEntries = allEntries.sorted(Comparator.comparing(BillEntry::timestamp));

        return sortedEntries.collect(Collectors.toUnmodifiableList());
    }

    public double totalOfIntermediateEntries() {
        return this.intermediateEntries
                .stream()
                .map(BillEntry::calculateSubTotal)
                .reduce(0.0, Double::sum);
    }

    public double calculateTotal() {

        double totalOfFinalBills = this.finalBills
                .stream().map(FinalBill::calculateTotal)
                .reduce(0.0, Double::sum);

        return totalOfFinalBills + this.totalOfIntermediateEntries();
    }

    public List<FinalBill> finalBills() {
        return Collections.unmodifiableList(this.finalBills);
    }

    public void assignResponsibility(RepresentativeDetails billingAddress, List<BillEntry> entries) {

        if(!this.intermediateEntries.containsAll(entries)) {
            throw new IllegalArgumentException();
        }

        this.intermediateEntries.removeAll(entries);

        this.finalBills.add(new FinalBill(billingAddress, entries));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Bill bill = (Bill) o;
        return Objects.equals(this.id, bill.id) && Objects.equals(this.domainId, bill.domainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.domainId);
    }


}

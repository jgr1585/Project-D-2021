package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.BillingService;
import fhv.teamd.hotel.application.dto.BillDTO;
import fhv.teamd.hotel.application.dto.BillEntryDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.domain.Bill;
import fhv.teamd.hotel.domain.BillEntry;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.BillId;
import fhv.teamd.hotel.domain.ids.StayId;
import fhv.teamd.hotel.domain.repositories.BillRepository;
import fhv.teamd.hotel.domain.repositories.FinalBillRepository;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private FinalBillRepository finalBillRepository;

    @Autowired
    private StayRepository stayRepository;

    @Override
    @Transactional
    public void assignPayments(String fromBillId, Predicate<BillEntryDTO> filter, RepresentativeDetails billingAddress) throws InvalidIdException {

        Optional<Bill> result = this.billRepository.findById(new BillId(fromBillId));

        Bill bill = result.orElseThrow(() -> new InvalidIdException("bill id"));

        List<BillEntry> assignableEntries = bill.intermediateEntries();

        List<BillEntry> selected = assignableEntries.stream()
                .filter(entry -> filter.test(BillEntryDTO.fromEntry(entry)))
                .collect(Collectors.toList());

        bill.assignResponsibility(selected, billingAddress, this.finalBillRepository::nextIdentity);

    }

    @Override
    @Transactional(readOnly = true)
    public BillDTO intermediateBill(String stayId) throws InvalidIdException {

        return BillDTO.fromBill(
                this.stayRepository.findById(new StayId(stayId))
                        .orElseThrow(() -> new InvalidIdException("stay id"))
                        .bill());

    }
}

package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.FinalBill;
import fhv.teamd.hotel.domain.ids.FinalBillId;
import fhv.teamd.hotel.domain.repositories.FinalBillRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateFinalBillRepository extends HibernateBaseRepository<FinalBill, FinalBillId> implements FinalBillRepository {
}

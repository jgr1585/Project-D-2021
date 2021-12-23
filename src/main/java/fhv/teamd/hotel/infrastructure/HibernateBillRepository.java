package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Bill;
import fhv.teamd.hotel.domain.ids.BillId;
import fhv.teamd.hotel.domain.repositories.BillRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateBillRepository extends HibernateBaseRepository<Bill, BillId> implements BillRepository {
}

package fhv.teamd.hotel.infrastructure;

import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public abstract class HibernateBaseRepository<T> {
    private final Class<T> genericType;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public HibernateBaseRepository() {
        this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(this.getClass(), HibernateBaseRepository.class);
    }

    public List<T> getAll() {

        CriteriaQuery<T> criteriaQuery = this.entityManager.getCriteriaBuilder().createQuery(this.genericType);
        criteriaQuery.select(criteriaQuery.from(this.genericType));

        return this.entityManager
                .createQuery(criteriaQuery)
                .getResultList();
    }
}

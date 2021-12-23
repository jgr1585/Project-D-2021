package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.ids.DomainId;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public abstract class HibernateBaseRepository<T, TId extends DomainId<T>> {
    private final Class<T> entityType;
    private final Class<TId> idType;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings({ "unchecked", "ConstantConditions" })
    protected HibernateBaseRepository() {

        Class<?>[] typeArgs = GenericTypeResolver.resolveTypeArguments(this.getClass(), HibernateBaseRepository.class);

        this.entityType = (Class<T>)typeArgs[0];
        this.idType = (Class<TId>)typeArgs[1];
    }

    public TId nextIdentity() {

        try {
            return this.idType.getConstructor(String.class).newInstance(UUID.randomUUID().toString());
        }
        catch (ReflectiveOperationException x) {
            throw new UnsupportedOperationException(x);
        }
    }

    public List<T> getAll() {

        CriteriaQuery<T> criteriaQuery = this.entityManager.getCriteriaBuilder().createQuery(this.entityType);
        criteriaQuery.select(criteriaQuery.from(this.entityType));

        return this.entityManager
                .createQuery(criteriaQuery)
                .getResultList();
    }

    public Optional<T> findById(TId id) {

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = cb.createQuery(this.entityType);

        Root<T> root = criteriaQuery.from(this.entityType);

        criteriaQuery.select(root).where(root.get("domainId").in(id));

        return this.entityManager
                .createQuery(criteriaQuery)
                .getResultList().stream()
                .findFirst();
    }
}

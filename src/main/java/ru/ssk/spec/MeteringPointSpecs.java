package ru.ssk.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.ssk.model.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

/**
 * Created by user on 30.05.2016.
 */
public class MeteringPointSpecs {

    private MeteringPointSpecs() {
        throw new AssertionError("Tried to instantiate Specs class!");
    }

    public static Specification<MeteringPoint> ownedByEntity(String ownerName) {
        return (root, query, builder) ->
                builder.like(
                        root.get(MeteringPoint_.owner).<String> get(LegalEntity_.name), "%" + ownerName + "%");

                  /*              .join(MeteringPoint_.owner)
                                .get(String.valueOf(LegalEntity_.name)),
                        ownerName);

            Subquery<Long> entityQuery = query.subquery(Long.class);
            Root<LegalEntity> legalEntityRoot = entityQuery.from(LegalEntity.class);
            Join<LegalEntity, MeteringPoint> points = legalEntityRoot.join(LegalEntity_.meteringPoints);
*/
    }

   /* public static Specification<Customer> isLongTermCustomer() {
        return new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {

                LocalDate date = new LocalDate().minusYears(2);
                return builder.lessThan(root.get(Customer_.createdAt), date);
            }
        };
    }*/
}

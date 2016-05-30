package ru.ssk.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.ssk.model.*;

import javax.persistence.criteria.*;
import java.sql.Date;

/**
 * Created by user on 30.05.2016.
 */
public class MeteringPointSpecs {

    private MeteringPointSpecs() {
        throw new AssertionError("Tried to instantiate Specs class!");
    }

    public static Specification<MeteringPoint> ownedBy(Long personalAccount) {
        return (root, query, builder) ->
                builder.equal(root.get(MeteringPoint_.owner).<Long> get(Owner_.personalAccount), personalAccount);
/*
            Subquery<Long> entityQuery = query.subquery(Long.class);
            Root<LegalEntity> legalEntityRoot = entityQuery.from(LegalEntity.class);
            Join<LegalEntity, MeteringPoint> points = legalEntityRoot.join(LegalEntity_.meteringPoints);
*/
    }

    public static Specification<MeteringPoint> locatedIn(Address address) {
        return (root, query, builder) -> {
            Path<Address> path = root.get(MeteringPoint_.address);
            return builder.and(
                    builder.like(path.get(Address_.region), "%" + address.getRegion() + "%"),
                    builder.like(path.get(Address_.city), "%" + address.getCity() + "%"),
                    builder.like(path.get(Address_.street), "%" + address.getStreet() + "%"),
                    builder.like(path.get(Address_.building), "%" + address.getBuilding() + "%"),
                    builder.like(path.get(Address_.apartment), "%" + address.getApartment() + "%"),
                    builder.equal(path.get(Address_.index), address.getIndex())
            );
        };
    }

    public static Specification<MeteringPoint> hasMeter(String manufacturer, String model, String serialNumber) {
        return (root, query, builder) -> {
            Path<Meter> path = root.get(MeteringPoint_.meter);
            return builder.and(
                    builder.like(path.get(Meter_.model).get(MeterModel_.manufacturer), "%" + manufacturer + "%"),
                    builder.like(path.get(Meter_.model).get(MeterModel_.name), "%" + model + "%"),
                    builder.equal(path.get(Meter_.serialNumber), serialNumber)
            );
        };
    }

    public static Specification<MeteringPoint> installedInPeriod(Date dateFrom, Date dateTo) {
        return (root, query, builder) -> builder.between(root.get(MeteringPoint_.installationDate), dateFrom, dateTo);
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

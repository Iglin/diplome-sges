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
    }

    public static Specification<MeteringPoint> hasNoAgreement() {
        return (root, query, builder) -> {
            Subquery<MeteringPoint> sq = query.subquery(MeteringPoint.class);
            Root<Agreement> project = sq.from(Agreement.class);
            Join<Agreement, MeteringPoint> sqPoint = project.join("meteringPoint");
            sq.select(sqPoint);
            return builder.not(builder.in(root).value(sq));
        };
    }

    public static Specification<MeteringPoint> hasEntityStatement() {
        return (root, query, builder) -> {
            Subquery<MeteringPoint> sq = query.subquery(MeteringPoint.class);
            Root<EntityStatement> project = sq.from(EntityStatement.class);
            Join<EntityStatement, MeteringPoint> sqPoint = project.join("meteringPoint");
            sq.select(sqPoint);
            return builder.in(root).value(sq);
        };
    }


    public static Specification<MeteringPoint> hasPersonStatement() {
        return (root, query, builder) -> {
            Subquery<MeteringPoint> sq = query.subquery(MeteringPoint.class);
            Root<PersonStatement> project = sq.from(PersonStatement.class);
            Join<PersonStatement, MeteringPoint> sqPoint = project.join("meteringPoint");
            sq.select(sqPoint);
            return builder.in(root).value(sq);
        };
    }
    public static Specification<MeteringPoint> locatedIn(String region, String city, String street, String building, String apartment, String index) {
        return (root, query, builder) -> {
            Path<Address> path = root.get(MeteringPoint_.address);
            Predicate predicate = null;
            if (region != null) {
                predicate = builder.like(path.get(Address_.region), "%" + region + "%");
            }
            if (city != null) {
                if (predicate == null) {
                    predicate = builder.like(path.get(Address_.city), "%" + city + "%");
                } else {
                    predicate = builder.and(predicate, builder.like(path.get(Address_.city), "%" + city + "%"));
                }
            }
            if (street != null) {
                if (predicate == null) {
                    predicate = builder.like(path.get(Address_.street), "%" + street + "%");
                } else {
                    predicate = builder.and(predicate, builder.like(path.get(Address_.street), "%" + street + "%"));
                }
            }
            if (building != null) {
                if (predicate == null) {
                    predicate = builder.like(path.get(Address_.building), "%" + building + "%");
                } else {
                    predicate = builder.and(predicate, builder.like(path.get(Address_.building), "%" + building + "%"));
                }
            }
            if (apartment != null) {
                if (predicate == null) {
                    predicate = builder.like(path.get(Address_.apartment), "%" + apartment + "%");
                } else {
                    predicate = builder.and(predicate, builder.like(path.get(Address_.apartment), "%" + apartment + "%"));
                }
            }
            if (index != null) {
                if (predicate == null) {
                    predicate = builder.equal(path.get(Address_.index), index);
                } else {
                    predicate = builder.and(predicate, builder.equal(path.get(Address_.index), index));
                }
            }
            return predicate;
        };
    }

    public static Specification<MeteringPoint> hasMeter(String manufacturer, String model, String serialNumber) {
        return (root, query, builder) -> {
            Path<Meter> path = root.get(MeteringPoint_.meter);
            Predicate predicate = null;
            if (manufacturer != null) {
                predicate = builder.like(path.get(Meter_.model).get(MeterModel_.manufacturer), "%" + manufacturer + "%");
            }
            if (model != null) {
                if (predicate == null) {
                    predicate = builder.like(path.get(Meter_.model).get(MeterModel_.name), "%" + model + "%");
                } else {
                    predicate = builder.and(predicate, builder.like(path.get(Meter_.model).get(MeterModel_.name), "%" + model + "%"));
                }
            }
            if (serialNumber != null) {
                if (predicate == null) {
                    predicate = builder.equal(path.get(Meter_.serialNumber), serialNumber);
                } else {
                    predicate = builder.and(predicate, builder.equal(path.get(Meter_.serialNumber), serialNumber));
                }
            }
            return predicate;
        };
    }

    public static Specification<MeteringPoint> installedInPeriod(Date dateFrom, Date dateTo) {
        return (root, query, builder) -> builder.between(root.get(MeteringPoint_.installationDate), dateFrom, dateTo);
    }
}

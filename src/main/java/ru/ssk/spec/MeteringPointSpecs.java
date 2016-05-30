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

    public static Specification<MeteringPoint> locatedIn(String region, String city, String street, String building, String apartment, String index) {
        return (root, query, builder) -> {
            Path<Address> path = root.get(MeteringPoint_.address);
            Predicate predicate = builder.and(
                    builder.like(path.get(Address_.region), "%" + region + "%"),
                    builder.like(path.get(Address_.city), "%" + city + "%"),
                    builder.like(path.get(Address_.street), "%" + street + "%"),
                    builder.like(path.get(Address_.building), "%" + building + "%"),
                    builder.like(path.get(Address_.apartment), "%" + apartment + "%"));
            if (index != null && !index.trim().equals("")) {
                predicate = builder.and(predicate, builder.equal(path.get(Address_.index), index));
            }
            return predicate;
        };
    }

    public static Specification<MeteringPoint> hasMeter(String manufacturer, String model, String serialNumber) {
        return (root, query, builder) -> {
            Path<Meter> path = root.get(MeteringPoint_.meter);
            Predicate predicate = builder.and(
                    builder.like(path.get(Meter_.model).get(MeterModel_.manufacturer), "%" + manufacturer + "%"),
                    builder.like(path.get(Meter_.model).get(MeterModel_.name), "%" + model + "%"));
            if (serialNumber != null && !serialNumber.trim().equals("")) {
                predicate = builder.and(predicate, builder.equal(path.get(Meter_.serialNumber), serialNumber));
            }
            return predicate;
        };
    }

    public static Specification<MeteringPoint> installedInPeriod(Date dateFrom, Date dateTo) {
        return (root, query, builder) -> builder.between(root.get(MeteringPoint_.installationDate), dateFrom, dateTo);
    }
}

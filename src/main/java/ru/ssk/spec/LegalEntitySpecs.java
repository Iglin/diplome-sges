package ru.ssk.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.ssk.model.Address;
import ru.ssk.model.Address_;
import ru.ssk.model.LegalEntity;
import ru.ssk.model.LegalEntity_;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.sql.Date;

/**
 * Created by user on 31.05.2016.
 */
public class LegalEntitySpecs {

    private LegalEntitySpecs() {
        throw new AssertionError("Tried to instantiate Specs class!");
    }

    public static Specification<LegalEntity> hasPersonalAccount(Long personalAccount) {
        return (root, query, builder) ->
                builder.equal(root.get(LegalEntity_.personalAccount), personalAccount);
    }

    public static Specification<LegalEntity> hasEmail(String email) {
        return (root, query, builder) ->
                builder.like(root.get(LegalEntity_.email), "%" + email + "%");
    }

    public static Specification<LegalEntity> hasName(String name) {
        return (root, query, builder) ->
                builder.like(root.get(LegalEntity_.name), "%" + name + "%");
    }

    public static Specification<LegalEntity> hasPhone(String phone) {
        phone = phone.replace(" ", "");
        phone = phone.replace("-", "");
        phone = phone.replace("(", "");
        phone = phone.replace(")", "");
        if (phone.length() == 11 && phone.startsWith("8")) {
            phone = "+7" + phone.substring(1);
        }
        String finalPhone = phone;
        return (root, query, builder) ->
                builder.like(root.get(LegalEntity_.name), "%" + finalPhone + "%");
    }

    public static Specification<LegalEntity> hasOgrn(String ogrn) {
        return (root, query, builder) ->
                builder.equal(root.get(LegalEntity_.ogrn), ogrn);
    }

    public static Specification<LegalEntity> hasInn(String inn) {
        return (root, query, builder) ->
                builder.equal(root.get(LegalEntity_.inn), inn);
    }

    public static Specification<LegalEntity> hasKpp(String kpp) {
        return (root, query, builder) ->
                builder.equal(root.get(LegalEntity_.kpp), kpp);
    }

    public static Specification<LegalEntity> registeredOn(Date date) {
        return (root, query, builder) ->
                builder.equal(root.get(LegalEntity_.registrationDate), date);
    }

    public static Specification<LegalEntity> hasAddress(String region, String city, String street, String building, String apartment, String index) {
        return (root, query, builder) -> {
            Path<Address> path = root.get(LegalEntity_.address);
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
}

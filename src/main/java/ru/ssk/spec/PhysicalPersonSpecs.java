package ru.ssk.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.ssk.model.*;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 * Created by user on 31.05.2016.
 */
public class PhysicalPersonSpecs {

    private PhysicalPersonSpecs() {
        throw new AssertionError("Tried to instantiate Specs class!");
    }

    public static Specification<PhysicalPerson> hasPersonalAccount(Long personalAccount) {
        return (root, query, builder) ->
                builder.equal(root.get(PhysicalPerson_.personalAccount), personalAccount);
    }

    public static Specification<PhysicalPerson> hasPassportNumber(Long passportNumber) {
        return (root, query, builder) ->
                builder.equal(root.get(PhysicalPerson_.passport).get(Passport_.passportNumber), passportNumber);
    }

    public static Specification<PhysicalPerson> hasName(String lastName, String firstName, String middleName) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (firstName != null && !firstName.equals("")) {
                predicate = builder.like(root.get(PhysicalPerson_.firstName), "%" + firstName + "%");
            }
            if (middleName != null && !middleName.equals("")) {
                if (predicate == null) {
                    predicate = builder.like(root.get(PhysicalPerson_.middleName), "%" + middleName + "%");
                } else {
                    predicate = builder.and(predicate, builder.like(root.get(PhysicalPerson_.middleName), "%" + middleName + "%"));
                }
            }
            if (lastName != null && !lastName.equals("")) {
                if (predicate == null) {
                    predicate = builder.like(root.get(PhysicalPerson_.lastName), "%" + lastName + "%");
                } else {
                    predicate = builder.and(predicate, builder.like(root.get(PhysicalPerson_.lastName), "%" + lastName + "%"));
                }
            }
            return predicate;
        };
    }

    public static Specification<PhysicalPerson> hasEmail(String email) {
        return (root, query, builder) ->
                builder.like(root.get(PhysicalPerson_.email), "%" + email + "%");
    }

    public static Specification<PhysicalPerson> hasPhone(String phone) {
        phone = phone.replace(" ", "");
        phone = phone.replace("-", "");
        phone = phone.replace("(", "");
        phone = phone.replace(")", "");
        if (phone.length() == 11 && phone.startsWith("8")) {
            phone = "+7" + phone.substring(1);
        }
        String finalPhone = phone;
        return (root, query, builder) ->
                builder.like(root.get(PhysicalPerson_.phone), "%" + finalPhone + "%");
    }

    public static Specification<PhysicalPerson> hasAddress(String region, String city, String street, String building, String apartment, String index) {
        return (root, query, builder) -> {
            Path<Address> path = root.get(PhysicalPerson_.livingAddress);
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

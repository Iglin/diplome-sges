package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.Address;
import ru.ssk.model.Passport;
import ru.ssk.model.PhysicalPerson;

import java.util.List;

/**
 * Created by root on 18.05.16.
 */
@Service
public interface PhysicalPersonService {
    PhysicalPerson save(PhysicalPerson owner);
    boolean delete(long id);
    void delete(PhysicalPerson owner);
    void deleteWithIds(List<Long> ids);
    PhysicalPerson findById(long id);
    PhysicalPerson findByPersonalAccount(long personalAccount);
    PhysicalPerson findByPhone(String phone);
    PhysicalPerson findByEmail(String email);

    List<PhysicalPerson> findByLastName(String lastName);
    List<PhysicalPerson> findByFirstName(String firstName);
    List<PhysicalPerson> findByMiddleName(String middleName);
    List<PhysicalPerson> findByFullName(String lastname, String firstName, String middleName);
    List<PhysicalPerson> findByLivingAddress(Address address);
    PhysicalPerson findByPassport(Passport passport);
    PhysicalPerson findByPassportNumber(long passportNumber);
    List<PhysicalPerson> findAll();
}

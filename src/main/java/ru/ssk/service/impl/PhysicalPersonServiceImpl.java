package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.Address;
import ru.ssk.model.Passport;
import ru.ssk.model.PhysicalPerson;
import ru.ssk.repository.PhysicalPersonRepository;
import ru.ssk.service.PhysicalPersonService;

import java.util.List;

/**
 * Created by root on 18.05.16.
 */
public class PhysicalPersonServiceImpl implements PhysicalPersonService {
    @Autowired
    private PhysicalPersonRepository personRepository;

    @Override
    public PhysicalPerson save(PhysicalPerson owner) {
        return personRepository.saveAndFlush(owner);
    }

    @Override
    public void delete(long id) {
        personRepository.delete(id);
    }

    @Override
    public void delete(PhysicalPerson owner) {
        personRepository.delete(owner);
    }

    @Override
    public PhysicalPerson findById(long id) {
        return personRepository.findOne(id);
    }

    @Override
    public PhysicalPerson findByPersonalAccount(long personalAccount) {
        return personRepository.findByPersonalAccount(personalAccount);
    }

    @Override
    public PhysicalPerson findByPhone(String phone) {
        return personRepository.findByPhone(phone);
    }

    @Override
    public PhysicalPerson findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Override
    public List<PhysicalPerson> findByLastName(String lastName) {
        return personRepository.findByLastName(lastName);
    }

    @Override
    public List<PhysicalPerson> findByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }

    @Override
    public List<PhysicalPerson> findByMiddleName(String middleName) {
        return personRepository.findByMiddleName(middleName);
    }

    @Override
    public List<PhysicalPerson> findByFullName(String lastname, String firstName, String middleName) {
        return personRepository.findByLastNameAndFirstNameAndMiddleName(lastname, firstName, middleName);
    }

    @Override
    public List<PhysicalPerson> findByLivingAddress(Address address) {
        return personRepository.findByLivingAddress(address);
    }

    @Override
    public PhysicalPerson findByPassport(Passport passport) {
        return personRepository.findByPassport(passport);
    }

    @Override
    public PhysicalPerson findByPassportNumber(long passportNumber) {
        return personRepository.findByPassportNumber(passportNumber);
    }

    @Override
    public List<PhysicalPerson> findAll() {
        return personRepository.findAll();
    }
}

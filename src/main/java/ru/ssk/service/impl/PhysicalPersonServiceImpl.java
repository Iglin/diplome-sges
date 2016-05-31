package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.exception.UniqueViolationException;
import ru.ssk.model.Address;
import ru.ssk.model.Passport;
import ru.ssk.model.PhysicalPerson;
import ru.ssk.repository.PassportRepository;
import ru.ssk.repository.PhysicalPersonRepository;
import ru.ssk.service.AddressService;
import ru.ssk.service.PassportService;
import ru.ssk.service.PhysicalPersonService;

import java.util.List;

/**
 * Created by root on 18.05.16.
 */

public class PhysicalPersonServiceImpl implements PhysicalPersonService {
    @Autowired
    private PhysicalPersonRepository personRepository;
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private PassportService passportService;

    @Override
    @Transactional
    public PhysicalPerson save(PhysicalPerson owner) {
        try {
            return personRepository.saveAndFlush(owner);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueViolationException("Нарушено ограничение при добавлении записи в базу.");
          /*
            Выкидывается ещё одно исключение!
         *
         *
         *
          personRepository.flush();
            if (ownerRepository.findByEmail(owner.getEmail()) != null) {
                throw new UniqueViolationException("В базе уже есть собственник с таким e-mail адресом.");
            } else if (ownerRepository.findByPersonalAccount(owner.getPersonalAccount()) != null) {
                throw new UniqueViolationException("В базе уже есть собственник с таким номером счёта.");
            } else if (ownerRepository.findByPhone(owner.getPhone()) != null) {
                throw new UniqueViolationException("В базе уже есть собственник с таким номером телефона.");
            } else if (personRepository.findByPassportNumber(owner.getPassport().getPassportNumber()) != null) {
                throw new UniqueViolationException("В базе уже есть собственник с таким номером паспорта.");
            } else {
                throw new UniqueViolationException("Нарушено ограничение при добавлении записи в базу.");
            }*/
        }
    }

    @Override
    public void delete(long id) {
        personRepository.delete(id);
        personRepository.flush();
        passportRepository.flush();
    }

    @Override
    public void delete(PhysicalPerson owner) {
        personRepository.delete(owner);
        personRepository.flush();
        passportRepository.flush();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        personRepository.deleteWithIds(ids);
        personRepository.flush();
        passportService.deleteOrphans();
        addressService.deleteOrphans();
      /*  ids.forEach(id -> {
            System.out.println("ID : " + id);
            PhysicalPerson person = personRepository.findById(id);
            Address livingAddress = person.getLivingAddress();
            Address registrationAddress = person.getPassport().getRegistrationAddress();
            delete(person);
            addressService.deleteIfOrphan(livingAddress);
            addressService.deleteIfOrphan(registrationAddress);
            System.out.println("FINISHED : " + id);
        });*/
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

    @Override
    public List<PhysicalPerson> findAll(Specification<PhysicalPerson> specification) {
        return personRepository.findAll(specification);
    }
}

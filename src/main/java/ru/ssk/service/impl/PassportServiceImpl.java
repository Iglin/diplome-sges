package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.Address;
import ru.ssk.model.Passport;
import ru.ssk.repository.PassportRepository;
import ru.ssk.service.AddressService;
import ru.ssk.service.PassportService;

import java.util.List;

/**
 * Created by user on 19.05.2016.
 */
@Transactional
public class PassportServiceImpl implements PassportService {
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private AddressService addressService;

    @Override
    public Passport findByPersonId(long id) {
        return passportRepository.findByPersonId(id);
    }

    @Override
    public List<Passport> findByPersonsIds(List<Long> ids) {
        return passportRepository.findByPersonsIds(ids);
    }

    @Override
    public void delete(Passport passport) {
        passportRepository.delete(passport);
        passportRepository.flush();
        addressService.deleteOrphans();
    }

    /**
     *
     * Must be bulk
     *
     **/
    @Override
    public void deleteWithOwnersIds(List<Long> ids) {
        List<Passport> passports = passportRepository.findByPersonsIds(ids);
        passports.forEach(passport -> passportRepository.delete(passport));
        passportRepository.flush();
        addressService.deleteOrphans();
    }

    @Override
    public void deleteOrphans() {
        passportRepository.deleteOrphans();
        passportRepository.flush();
    }
}

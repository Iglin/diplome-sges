package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import ru.ssk.model.Address;
import ru.ssk.model.LegalEntity;
import ru.ssk.repository.LegalEntityRepository;
import ru.ssk.service.AddressService;
import ru.ssk.service.LegalEntityService;

import java.util.List;

/**
 * Created by user on 23.05.2016.
 */
public class LegalEntityServiceImpl implements LegalEntityService {
    @Autowired
    private LegalEntityRepository legalEntityRepository;
    @Autowired
    private AddressService addressService;

    @Override
    public LegalEntity save(LegalEntity legalEntity) {
        return legalEntityRepository.saveAndFlush(legalEntity);
    }

    @Override
    public void delete(long id) {
        legalEntityRepository.delete(id);
        legalEntityRepository.flush();
        addressService.deleteOrphans();
    }

    @Override
    public void delete(LegalEntity legalEntity) {
        legalEntityRepository.delete(legalEntity);
        legalEntityRepository.flush();
        addressService.deleteOrphans();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        legalEntityRepository.deleteWithIds(ids);
        legalEntityRepository.flush();
        addressService.deleteOrphans();
    }

    @Override
    public LegalEntity findById(Long id) {
        return legalEntityRepository.findById(id);
    }

    @Override
    public LegalEntity findByPersonalAccount(Long personalAccount) {
        return legalEntityRepository.findByPersonalAccount(personalAccount);
    }

    @Override
    public LegalEntity findByEmail(String email) {
        return legalEntityRepository.findByEmail(email);
    }

    @Override
    public LegalEntity findByPhone(String phone) {
        return legalEntityRepository.findByPhone(phone);
    }

    @Override
    public LegalEntity findByOgrn(String ogrn) {
        return legalEntityRepository.findByOgrn(ogrn);
    }

    @Override
    public List<LegalEntity> findByInn(String inn) {
        return legalEntityRepository.findByInn(inn);
    }

    @Override
    public List<LegalEntity> findByKpp(String kpp) {
        return legalEntityRepository.findByKpp(kpp);
    }

    @Override
    public List<LegalEntity> findByName(String name) {
        return legalEntityRepository.findByName(name);
    }

    @Override
    public List<LegalEntity> findByAddress(Address address) {
        return legalEntityRepository.findByAddress(address);
    }

    @Override
    public List<LegalEntity> findByInnAndKpp(String inn, String kpp) {
        return legalEntityRepository.findByInnAndKpp(inn, kpp);
    }

    @Override
    public List<LegalEntity> findAll() {
        return legalEntityRepository.findAll();
    }

    @Override
    public List<LegalEntity> findAll(Specification<LegalEntity> specification) {
        return legalEntityRepository.findAll(specification);
    }
}

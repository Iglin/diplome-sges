package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.Address;
import ru.ssk.model.LegalEntity;

import java.util.List;

/**
 * Created by user on 23.05.2016.
 */
@Service
public interface LegalEntityService {
    LegalEntity save(LegalEntity legalEntity);
    void delete(long id);
    void delete(LegalEntity legalEntity);
    void deleteWithIds(List<Long> ids);

    LegalEntity findById(Long id);
    LegalEntity findByPersonalAccount(Long personalAccount);
    LegalEntity findByEmail(String email);
    LegalEntity findByPhone(String phone);
    LegalEntity findByOgrn(String ogrn);
    List<LegalEntity> findByInn(String inn);
    List<LegalEntity> findByKpp(String kpp);
    List<LegalEntity> findByName(String name);
    List<LegalEntity> findByAddress(Address address);
    List<LegalEntity> findByInnAndKpp(String inn, String kpp);
    List<LegalEntity> findAll();
}

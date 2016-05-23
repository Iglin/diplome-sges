package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.Address;
import ru.ssk.model.LegalEntity;

import java.util.List;

/**
 * Created by user on 23.05.2016.
 */
@Repository
@Transactional
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long>, JpaSpecificationExecutor {
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

    @Modifying
    @Query("delete from LegalEntity a where a.id in ?1")
    void deleteWithIds(List<Long> ids);
}

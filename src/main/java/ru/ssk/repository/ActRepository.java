package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.ActOfProvidingServices;
import ru.ssk.model.Agreement;

import java.util.List;

/**
 * Created by user on 10.06.2016.
 */
@Repository
@Transactional
public interface ActRepository extends JpaRepository<ActOfProvidingServices, Long> {
    ActOfProvidingServices findByAgreement(Agreement agreement);

    @Query("SELECT a FROM ActOfProvidingServices a where a.agreement.number = ?1")
    ActOfProvidingServices findByAgreementNumber(long number);

    @Modifying
    @Query("DELETE FROM ActOfProvidingServices s WHERE s.agreement.number IN ?1")
    void deleteWithAgreementNumbers(List<Long> numbers);

    @Modifying
    @Query("delete from ActOfProvidingServices a where a.number in ?1")
    void deleteWithIds(List<Long> ids);
}

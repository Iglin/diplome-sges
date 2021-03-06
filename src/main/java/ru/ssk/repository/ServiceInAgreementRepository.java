package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.Agreement;
import ru.ssk.model.ExtraService;
import ru.ssk.model.ServiceInAgreement;

import java.util.List;

/**
 * Created by user on 01.06.2016.
 */
@Repository
@Transactional
public interface ServiceInAgreementRepository extends JpaRepository<ServiceInAgreement, Long> {
    List<ServiceInAgreement> findByExtraService(ExtraService extraService);
    List<ServiceInAgreement> findByAgreement(Agreement agreement);
    ServiceInAgreement findByExtraServiceAndAgreement(ExtraService extraService, Agreement agreement);

    @Modifying
    @Query("DELETE FROM ServiceInAgreement s WHERE s.agreement.number IN ?1")
    void deleteWithAgreementsNumbers(List<Long> numbers);

    @Modifying
    @Query("DELETE FROM ServiceInAgreement s WHERE NOT s.extraService.id IN ?1 AND s.agreement.number = ?2")
    void deleteOldServices(List<Long> serviceIds, Long agreementNumber);
}

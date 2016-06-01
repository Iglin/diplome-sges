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
}

package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.Agreement;
import ru.ssk.model.ExtraService;
import ru.ssk.model.ServiceInAgreement;

import java.util.List;

/**
 * Created by user on 01.06.2016.
 */
@Service
public interface ServiceInAgreementService {
    ServiceInAgreement save(ServiceInAgreement serviceInAgreement);
    void delete(ServiceInAgreement serviceInAgreement);
    List<ServiceInAgreement> findByExtraService(ExtraService extraService);
    List<ServiceInAgreement> findByAgreement(Agreement agreement);
    ServiceInAgreement findByExtraServiceAndAgreement(ExtraService extraService, Agreement agreement);
    List< ServiceInAgreement> findAll();
}

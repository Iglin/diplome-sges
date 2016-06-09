package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.Agreement;
import ru.ssk.model.ExtraService;
import ru.ssk.model.ServiceInAgreement;
import ru.ssk.repository.ServiceInAgreementRepository;
import ru.ssk.service.ServiceInAgreementService;

import java.util.List;

/**
 * Created by user on 01.06.2016.
 */
public class ServiceInAgreementServiceImpl implements ServiceInAgreementService {
    @Autowired
    private ServiceInAgreementRepository inAgreementRepository;
    @Override
    public ServiceInAgreement save(ServiceInAgreement serviceInAgreement) {
        return inAgreementRepository.saveAndFlush(serviceInAgreement);
    }

    @Override
    public void delete(ServiceInAgreement serviceInAgreement) {
        inAgreementRepository.delete(serviceInAgreement);
    }

    @Override
    public List<ServiceInAgreement> findByExtraService(ExtraService extraService) {
        return inAgreementRepository.findByExtraService(extraService);
    }

    @Override
    public List<ServiceInAgreement> findByAgreement(Agreement agreement) {
        return inAgreementRepository.findByAgreement(agreement);
    }

    @Override
    public ServiceInAgreement findByExtraServiceAndAgreement(ExtraService extraService, Agreement agreement) {
        return inAgreementRepository.findByExtraServiceAndAgreement(extraService, agreement);
    }

    @Override
    public List<ServiceInAgreement> findAll() {
        return inAgreementRepository.findAll();
    }

    @Override
    public void deleteWithAgreementsNumbers(List<Long> agreementNumbers) {
        inAgreementRepository.deleteWithAgreementsNumbers(agreementNumbers);
        inAgreementRepository.flush();
    }

    @Override
    public void deleteOldServices(List<Long> actualServicesIds, Long agreementNumber) {
        inAgreementRepository.deleteOldServices(actualServicesIds, agreementNumber);
        inAgreementRepository.flush();
    }
}

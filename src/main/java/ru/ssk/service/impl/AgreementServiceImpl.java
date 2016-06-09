package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.Agreement;
import ru.ssk.model.MeteringPoint;
import ru.ssk.repository.AgreementRepository;
import ru.ssk.service.AgreementService;
import ru.ssk.service.ServiceInAgreementService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 01.06.2016.
 */
public class AgreementServiceImpl implements AgreementService {
    @Autowired
    private AgreementRepository agreementRepository;
    @Autowired
    private ServiceInAgreementService serviceInAgreementService;

    @Override
    public Agreement save(Agreement agreement, boolean isUpdate) {
        if (isUpdate) {
            List<Long> ids = new ArrayList<>(agreement.getServices().size());
            agreement.getServices().forEach(serviceInAgreement -> ids.add(serviceInAgreement.getExtraService().getId()));
            serviceInAgreementService.deleteOldServices(ids, agreement.getNumber());
            agreementRepository.flush();
        }
        return agreementRepository.saveAndFlush(agreement);
    }

    @Override
    public void delete(long id) {
        agreementRepository.delete(id);
        agreementRepository.flush();
    }

    @Override
    public void delete(Agreement agreement) {
        agreementRepository.delete(agreement);
        agreementRepository.flush();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        serviceInAgreementService.deleteWithAgreementsNumbers(ids);
        agreementRepository.deleteWithIds(ids);
        agreementRepository.flush();
    }

    @Override
    public Agreement findByNumber(Long number) {
        return agreementRepository.findOne(number);
    }

    @Override
    public Agreement findByMeteringPoint(MeteringPoint meteringPoint) {
        return agreementRepository.findByMeteringPoint(meteringPoint);
    }

    @Override
    public List<Agreement> findAll() {
        return agreementRepository.findAll();
    }
}

package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.Agreement;
import ru.ssk.model.MeteringPoint;
import ru.ssk.repository.AgreementRepository;
import ru.ssk.service.AgreementService;

import java.util.List;

/**
 * Created by user on 01.06.2016.
 */
public class AgreementServiceImpl implements AgreementService {
    @Autowired
    private AgreementRepository agreementRepository;

    @Override
    public Agreement save(Agreement agreement) {
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

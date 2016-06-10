package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.ActOfProvidingServices;
import ru.ssk.model.Agreement;
import ru.ssk.repository.ActRepository;
import ru.ssk.service.ActService;

import java.util.List;

/**
 * Created by user on 10.06.2016.
 */
public class ActServiceImpl implements ActService {
    @Autowired
    private ActRepository actRepository;

    @Override
    public ActOfProvidingServices save(ActOfProvidingServices act) {
        return actRepository.saveAndFlush(act);
    }

    @Override
    public void delete(long number) {
        actRepository.delete(number);
        actRepository.flush();
    }

    @Override
    public void delete(ActOfProvidingServices act) {
        actRepository.delete(act);
        actRepository.flush();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        actRepository.deleteWithIds(ids);
        actRepository.flush();
    }

    @Override
    public void deleteWithAgreementNumbers(List<Long> numbers) {
        actRepository.deleteWithAgreementNumbers(numbers);
        actRepository.flush();
    }

    @Override
    public ActOfProvidingServices findByNumber(long number) {
        return actRepository.findOne(number);
    }

    @Override
    public ActOfProvidingServices findByAgreement(Agreement agreement) {
        return actRepository.findByAgreement(agreement);
    }

    @Override
    public ActOfProvidingServices findByAgreementNumber(long number) {
        return actRepository.findByAgreementNumber(number);
    }

    @Override
    public List<ActOfProvidingServices> findAll() {
        return actRepository.findAll();
    }
}

package ru.ssk.service;

import ru.ssk.model.ActOfProvidingServices;
import ru.ssk.model.Agreement;

import java.util.List;

/**
 * Created by user on 10.06.2016.
 */
public interface ActService {
    ActOfProvidingServices save(ActOfProvidingServices act);
    void delete(long number);
    void delete(ActOfProvidingServices act);
    void deleteWithIds(List<Long> ids);
    void deleteWithAgreementNumbers(List<Long> numbers);
    ActOfProvidingServices findByNumber(long number);
    ActOfProvidingServices findByAgreement(Agreement agreement);
    ActOfProvidingServices findByAgreementNumber(long number);
    List<ActOfProvidingServices> findAll();
}

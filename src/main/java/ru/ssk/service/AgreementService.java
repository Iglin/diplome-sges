package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.Agreement;
import ru.ssk.model.MeteringPoint;

import java.util.List;

/**
 * Created by user on 01.06.2016.
 */
@Service
public interface AgreementService {
    Agreement save(Agreement agreement, boolean isUpdate);
    void delete(long id);
    void delete(Agreement agreement);
    void deleteWithIds(List<Long> ids);
    Agreement findByNumber(Long number);
    Agreement findByMeteringPoint(MeteringPoint meteringPoint);
    List<Agreement> findAll();
}

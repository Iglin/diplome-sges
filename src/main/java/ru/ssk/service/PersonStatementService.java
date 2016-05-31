package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.MeteringPoint;
import ru.ssk.model.PersonStatement;

import java.sql.Date;
import java.util.List;

/**
 * Created by user on 31.05.2016.
 */
@Service
public interface PersonStatementService {
    PersonStatement save(PersonStatement personStatement);
    void delete(long id);
    void delete(PersonStatement personStatement);
    PersonStatement findById(long id);
    PersonStatement findByNumber(long number);
    PersonStatement findByMeteringPoint(MeteringPoint meteringPoint);
    List<PersonStatement> findByDate(Date date);
    List<PersonStatement> findByHousing(boolean housing);
    List<PersonStatement> findAll();

    void deleteWithIds(List<Long> ids);
}

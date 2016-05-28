package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.EntityStatement;
import ru.ssk.model.MeteringPoint;

import java.sql.Date;
import java.util.List;

/**
 * Created by user on 28.05.2016.
 */
@Service
public interface EntityStatementService {
    EntityStatement save(EntityStatement entityStatement);
    void delete(long id);
    void delete(EntityStatement entityStatement);
    EntityStatement findById(long id);
    EntityStatement findByNumber(long number);
    EntityStatement findByMeteringPoint(MeteringPoint meteringPoint);
    List<EntityStatement> findByDate(Date date);
    List<EntityStatement> findByHousing(boolean housing);
    List<EntityStatement> findAll();

    void deleteWithIds(List<Long> ids);
}

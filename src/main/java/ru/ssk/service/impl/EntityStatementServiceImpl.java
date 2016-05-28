package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.EntityStatement;
import ru.ssk.model.MeteringPoint;
import ru.ssk.repository.EntityStatementRepository;
import ru.ssk.service.EntityStatementService;

import java.sql.Date;
import java.util.List;

/**
 * Created by user on 28.05.2016.
 */
public class EntityStatementServiceImpl implements EntityStatementService {
    @Autowired
    private EntityStatementRepository entityStatementRepository;
    @Override
    public EntityStatement save(EntityStatement entityStatement) {
        return entityStatementRepository.saveAndFlush(entityStatement);
    }

    @Override
    public void delete(long id) {
        entityStatementRepository.delete(id);
    }

    @Override
    public void delete(EntityStatement entityStatement) {
        entityStatementRepository.delete(entityStatement);
    }

    @Override
    public EntityStatement findById(long id) {
        return entityStatementRepository.findOne(id);
    }

    @Override
    public EntityStatement findByNumber(long number) {
        return entityStatementRepository.findByNumber(number);
    }

    @Override
    public EntityStatement findByMeteringPoint(MeteringPoint meteringPoint) {
        return entityStatementRepository.findByMeteringPoint(meteringPoint);
    }

    @Override
    public List<EntityStatement> findByDate(Date date) {
        return entityStatementRepository.findByDate(date);
    }

    @Override
    public List<EntityStatement> findByHousing(boolean housing) {
        return entityStatementRepository.findByHousing(housing);
    }

    @Override
    public List<EntityStatement> findAll() {
        return entityStatementRepository.findAll();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        entityStatementRepository.deleteWithIds(ids);
    }
}

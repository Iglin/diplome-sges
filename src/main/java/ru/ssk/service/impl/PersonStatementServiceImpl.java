package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.MeteringPoint;
import ru.ssk.model.PersonStatement;
import ru.ssk.repository.PersonStatementRepository;
import ru.ssk.service.PersonStatementService;

import java.sql.Date;
import java.util.List;

/**
 * Created by user on 31.05.2016.
 */
public class PersonStatementServiceImpl implements PersonStatementService {
    @Autowired
    private PersonStatementRepository personStatementRepository;

    @Override
    public PersonStatement save(PersonStatement personStatement) {
        return personStatementRepository.saveAndFlush(personStatement);
    }

    @Override
    public void delete(long id) {
        personStatementRepository.delete(id);
        personStatementRepository.flush();
    }

    @Override
    public void delete(PersonStatement personStatement) {
        personStatementRepository.delete(personStatement);
        personStatementRepository.flush();
    }

    @Override
    public PersonStatement findById(long id) {
        return personStatementRepository.findOne(id);
    }

    @Override
    public PersonStatement findByNumber(long number) {
        return personStatementRepository.findByNumber(number);
    }

    @Override
    public PersonStatement findByMeteringPoint(MeteringPoint meteringPoint) {
        return personStatementRepository.findByMeteringPoint(meteringPoint);
    }

    @Override
    public List<PersonStatement> findByDate(Date date) {
        return personStatementRepository.findByDate(date);
    }

    @Override
    public List<PersonStatement> findByHasElevator(boolean hasElevator) {
        return personStatementRepository.findByHasElevator(hasElevator);
    }

    @Override
    public List<PersonStatement> findAll() {
        return personStatementRepository.findAll();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        personStatementRepository.deleteWithIds(ids);
    }
}

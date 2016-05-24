package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.Enterprise;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
@Service
public interface EnterpriseService {
    Enterprise save(Enterprise enterprise);
    void delete(long id);
    void delete(Enterprise enterprise);
    void deleteWithIds(List<Long> ids);

    void setActual(Enterprise enterprise);
    void setActual(long id);

    Enterprise findById(Long id);
    Enterprise findActual();
    List<Enterprise> findByDirector(String director);
    List<Enterprise> findByRegistryChief(String registryChief);
    List<Enterprise> findAll();
}

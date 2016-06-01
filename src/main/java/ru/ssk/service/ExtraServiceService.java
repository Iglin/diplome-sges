package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.ExtraService;

import java.util.List;

/**
 * Created by user on 01.06.2016.
 */
@Service
public interface ExtraServiceService {
    ExtraService save(ExtraService extraService);
    void delete(long id);
    void delete(ExtraService extraService);
    void deleteWithIds(List<Long> ids);
    ExtraService findById(Long id);
    List<ExtraService> findByName(String name);
    List<ExtraService> findAll();
}

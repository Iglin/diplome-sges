package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.ExtraService;
import ru.ssk.repository.ExtraServiceRepository;
import ru.ssk.service.ExtraServiceService;

import java.util.List;

/**
 * Created by user on 01.06.2016.
 */
public class ExtraServiceServiceImpl implements ExtraServiceService {
    @Autowired
    private ExtraServiceRepository extraServiceRepository;

    @Override
    public ExtraService save(ExtraService extraService) {
        return extraServiceRepository.saveAndFlush(extraService);
    }

    @Override
    public void delete(long id) {
        extraServiceRepository.delete(id);
        extraServiceRepository.flush();
    }

    @Override
    public void delete(ExtraService extraService) {
        extraServiceRepository.delete(extraService);
        extraServiceRepository.flush();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        extraServiceRepository.deleteWithIds(ids);
        extraServiceRepository.flush();
    }

    @Override
    public ExtraService findById(Long id) {
        return extraServiceRepository.findOne(id);
    }

    @Override
    public List<ExtraService> findByName(String name) {
        return extraServiceRepository.findByName(name);
    }

    @Override
    public List<ExtraService> findAll() {
        return extraServiceRepository.findAll();
    }
}

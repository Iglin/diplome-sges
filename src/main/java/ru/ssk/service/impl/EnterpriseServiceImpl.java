package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.Enterprise;
import ru.ssk.repository.EnterpriseRepository;
import ru.ssk.service.AddressService;
import ru.ssk.service.EnterpriseService;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
public class EnterpriseServiceImpl implements EnterpriseService {
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private AddressService addressService;

    @Override
    public Enterprise save(Enterprise enterprise) {
        return enterpriseRepository.saveAndFlush(enterprise);
    }

    @Override
    public void setActual(long id) {
        enterpriseRepository.clearActuals();
        enterpriseRepository.setActual(id);
        enterpriseRepository.flush();
    }

    @Override
    public void setActual(Enterprise enterprise) {
        enterpriseRepository.clearActuals();
        enterpriseRepository.setActual(enterprise.getId());
        enterpriseRepository.flush();
    }

    @Override
    public void delete(long id) {
        enterpriseRepository.delete(id);
        enterpriseRepository.flush();
        addressService.deleteOrphans();
    }

    @Override
    public void delete(Enterprise enterprise) {
        enterpriseRepository.delete(enterprise);
        enterpriseRepository.flush();
        addressService.deleteOrphans();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        enterpriseRepository.deleteWithIds(ids);
        enterpriseRepository.flush();
        addressService.deleteOrphans();
    }

    @Override
    public Enterprise findById(Long id) {
        return enterpriseRepository.findOne(id);
    }

    @Override
    public Enterprise findActual() {
        return enterpriseRepository.findActual();
    }

    @Override
    public List<Enterprise> findByDirector(String director) {
        return enterpriseRepository.findByDirector(director);
    }

    @Override
    public List<Enterprise> findByRegistryChief(String registryChief) {
        return enterpriseRepository.findByRegistryChief(registryChief);
    }

    @Override
    public List<Enterprise> findAll() {
        return enterpriseRepository.findAll();
    }
}

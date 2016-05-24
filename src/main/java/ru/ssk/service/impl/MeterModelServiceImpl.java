package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.MeterModel;
import ru.ssk.repository.MeterModelRepository;
import ru.ssk.service.MeterModelService;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
public class MeterModelServiceImpl implements MeterModelService {
    @Autowired
    private MeterModelRepository meterModelRepository;

    @Override
    public MeterModel save(MeterModel meterModel) {
        return meterModelRepository.saveAndFlush(meterModel);
    }

    @Override
    public void delete(long id) {
        meterModelRepository.delete(id);
        meterModelRepository.flush();
    }

    @Override
    public void delete(MeterModel meterModel) {
        meterModelRepository.delete(meterModel);
        meterModelRepository.flush();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        meterModelRepository.deleteWithIds(ids);
        meterModelRepository.flush();
    }

    @Override
    public MeterModel findById(long id) {
        return meterModelRepository.findOne(id);
    }

    @Override
    public List<MeterModel> findByName(String name) {
        return meterModelRepository.findByName(name);
    }

    @Override
    public List<MeterModel> findByManufacturer(String manufacturer) {
        return meterModelRepository.findByManufacturer(manufacturer);
    }

    @Override
    public List<MeterModel> findByNameAndManufacturer(String name, String manufacturer) {
        return meterModelRepository.findByNameAndManufacturer(name, manufacturer);
    }

    @Override
    public List<MeterModel> findAll() {
        return meterModelRepository.findAll();
    }
}

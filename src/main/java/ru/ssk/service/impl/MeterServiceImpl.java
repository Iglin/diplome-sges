package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.Meter;
import ru.ssk.model.MeterModel;
import ru.ssk.repository.MeterRepository;
import ru.ssk.service.MeterService;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
public class MeterServiceImpl implements MeterService {
    @Autowired
    private MeterRepository meterRepository;

    @Override
    public Meter save(Meter meter) {
        return meterRepository.saveAndFlush(meter);
    }

    @Override
    public void delete(long id) {
        meterRepository.delete(id);
        meterRepository.flush();
    }

    @Override
    public void delete(Meter meter) {
        meterRepository.delete(meter);
        meterRepository.flush();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        meterRepository.deleteWithIds(ids);
        meterRepository.flush();
    }

    @Override
    public Meter findById(long id) {
        return meterRepository.findOne(id);
    }

    @Override
    public List<Meter> findBySerialNumber(String serialNumber) {
        return meterRepository.findBySerialNumber(serialNumber);
    }

    @Override
    public List<Meter> findByProductionYear(int productionYear) {
        return meterRepository.findByProductionYear(productionYear);
    }

    @Override
    public List<Meter> findByModel(MeterModel model) {
        return meterRepository.findByModel(model);
    }

    @Override
    public List<Meter> findAll() {
        return meterRepository.findAll();
    }
}

package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.Meter;
import ru.ssk.model.MeterModel;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
@Service
public interface MeterService {
    Meter save(Meter meter);
    void delete(long id);
    void delete(Meter meter);
    void deleteWithIds(List<Long> ids);
    Meter findById(long id);
    List<Meter> findBySerialNumber(String serialNumber);
    List<Meter> findByProductionYear(int productionYear);
    List<Meter> findByModel(MeterModel model);
    List<Meter> findAll();
}

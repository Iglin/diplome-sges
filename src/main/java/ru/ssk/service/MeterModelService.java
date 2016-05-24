package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.MeterModel;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
@Service
public interface MeterModelService {
    MeterModel save(MeterModel meterModel);
    void delete(long id);
    void delete(MeterModel meterModel);
    void deleteWithIds(List<Long> ids);
    MeterModel findById(long id);
    List<MeterModel> findByName(String name);
    List<MeterModel> findByManufacturer(String manufacturer);
    List<MeterModel> findByNameAndManufacturer(String name, String manufacturer);
    List<MeterModel> findAll();
}

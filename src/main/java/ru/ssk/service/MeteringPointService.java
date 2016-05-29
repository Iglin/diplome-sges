package ru.ssk.service;

import ru.ssk.model.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by user on 25.05.2016.
 */
public interface MeteringPointService {
    MeteringPoint save(MeteringPoint meteringPoint);
    void delete(MeteringPoint meteringPoint);
    void delete(long id);

    void deleteWithIds(List<Long> ids);

    MeteringPoint findById(long id);
    List<MeteringPoint> findByInstallationDate(Date date);
    List<MeteringPoint> findByMeter(Meter meter);
    List<MeteringPoint> findByOwner(Owner owner);
    List<MeteringPoint> findByAddress(Address address);
    List<MeteringPoint> findByEnterpriseEntry(Enterprise enterprise);
    List<MeteringPoint> findAllEntityPoints();
    List<MeteringPoint> findAll();
}

package ru.ssk.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ssk.model.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by user on 25.05.2016.
 */
@Service
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
    List<MeteringPoint> findAllPersonPoints();
    List<MeteringPoint> findEntityPointsWithStatements();
    List<MeteringPoint> findPersonPointsWithStatements();
    List<MeteringPoint> findAll();
    List<MeteringPoint> findAll(Specification<MeteringPoint> specification);
}

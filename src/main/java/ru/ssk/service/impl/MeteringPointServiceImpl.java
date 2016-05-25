package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.*;
import ru.ssk.repository.MeteringPointRepository;
import ru.ssk.service.AddressService;
import ru.ssk.service.MeteringPointService;

import java.sql.Date;
import java.util.List;

/**
 * Created by user on 25.05.2016.
 */
public class MeteringPointServiceImpl implements MeteringPointService {
    @Autowired
    private MeteringPointRepository meteringPointRepository;
    @Autowired
    private AddressService addressService;

    @Override
    public MeteringPoint save(MeteringPoint meteringPoint) {
        return meteringPointRepository.saveAndFlush(meteringPoint);
    }

    @Override
    public void delete(MeteringPoint meteringPoint) {
        meteringPointRepository.delete(meteringPoint);
        meteringPointRepository.flush();
        addressService.deleteOrphans();
    }

    @Override
    public void delete(long id) {
        meteringPointRepository.delete(id);
        meteringPointRepository.flush();
        addressService.deleteOrphans();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        meteringPointRepository.deleteWithIds(ids);
        meteringPointRepository.flush();
        addressService.deleteOrphans();
    }

    @Override
    public MeteringPoint findById(long id) {
        return meteringPointRepository.findOne(id);
    }

    @Override
    public List<MeteringPoint> findByInstallationDate(Date date) {
        return meteringPointRepository.findByInstallationDate(date);
    }

    @Override
    public List<MeteringPoint> findByMeter(Meter meter) {
        return meteringPointRepository.findByMeter(meter);
    }

    @Override
    public List<MeteringPoint> findByOwner(Owner owner) {
        return meteringPointRepository.findByOwner(owner);
    }

    @Override
    public List<MeteringPoint> findByAddress(Address address) {
        return meteringPointRepository.findByAddress(address);
    }

    @Override
    public List<MeteringPoint> findByEnterpriseEntry(Enterprise enterprise) {
        return meteringPointRepository.findByEnterpriseEntry(enterprise);
    }

    @Override
    public List<MeteringPoint> findAll() {
        return meteringPointRepository.findAll();
    }
}

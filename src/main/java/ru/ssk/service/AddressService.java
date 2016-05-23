package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.Address;

import java.util.List;

/**
 * Created by user on 15.05.2016.
 */
@Service
public interface AddressService {
    Address save(Address address);
    void delete(long id);
    void delete(Address address);
    void deleteAddressesWithIds(List<Long> ids);
    void deleteIfOrphan(Address address);
    void deleteOrphans();
    Address findById(long id);
    List<Address> findAll();
    List<Address> findByRegion(String region);
    List<Address> findByCity(String city);
    List<Address> findByStreet(String street);
    List<Address> findByBuilding(String building);
    List<Address> findByApartment(String apartment);
    List<Address> findByIndex(int index);
}

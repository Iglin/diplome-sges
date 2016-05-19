package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.exception.DuplicateDataException;
import ru.ssk.model.Address;
import ru.ssk.repository.AddressRepository;
import ru.ssk.service.AddressService;

import java.util.List;

/**
 * Created by user on 15.05.2016.
 */
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        List<Address> duplicates = addressRepository.findByRegionAndCityAndStreetAndBuildingAndApartmentAndIndex(address.getRegion(),
                address.getCity(), address.getStreet(), address.getBuilding(), address.getApartment(), address.getIndex());
        if (duplicates == null || duplicates.isEmpty() ||
                (duplicates.size() == 1 && address.getId() != null && duplicates.get(0).getId().equals(address.getId()))) {
            return addressRepository.saveAndFlush(address);
        } else {
            throw new DuplicateDataException("В базе уже зарегестрирован такой адрес.");
        }
    }

    @Override
    public void delete(long id) {
        addressRepository.delete(id);
        addressRepository.flush();
    }

    @Override
    public void delete(Address address) {
        addressRepository.delete(address);
        addressRepository.flush();
    }

    @Transactional
    @Override
    public void deleteAddressesWithIds(List<Long> ids) {
        addressRepository.deleteAddressesWithIds(ids);
        addressRepository.flush();
    }

    @Override
    public void deleteIfOrphan(Address address) {
        address = addressRepository.findOne(address.getId());
        System.out.println("Persons : " + address.getPersons().size());
        System.out.println("Passports : " + address.getPassports().size());
        if (address.getPersons().isEmpty() && address.getPassports().isEmpty()) {
            addressRepository.delete(address);
            addressRepository.flush();
        }
    }

    @Override
   // @Transactional
    public Address findById(long id) {
        return addressRepository.findOne(id);
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> findByRegion(String region) {
        return addressRepository.findByRegion(region);
    }

    @Override
    public List<Address> findByCity(String city) {
        return addressRepository.findByCity(city);
    }

    @Override
    public List<Address> findByStreet(String street) {
        return addressRepository.findByStreet(street);
    }

    @Override
    public List<Address> findByBuilding(String building) {
        return addressRepository.findByBuilding(building);
    }

    @Override
    public List<Address> findByApartment(String apartment) {
        return addressRepository.findByApartment(apartment);
    }

    @Override
    public List<Address> findByIndex(int index) {
        return addressRepository.findByIndex(index);
    }
}

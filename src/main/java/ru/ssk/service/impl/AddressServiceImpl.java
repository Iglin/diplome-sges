package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.exception.DuplicateDataException;
import ru.ssk.model.Address;
import ru.ssk.repository.AddressRepository;
import ru.ssk.service.AddressService;

import java.util.List;
import java.util.Optional;

/**
 * Created by user on 15.05.2016.
 */
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address add(Address address) {
        Address duplicate = addressRepository.findByRegionAndCityAndStreetAndBuildingAndApartmentAndIndex(address.getRegion(),
                address.getCity(), address.getStreet(), address.getBuilding(), address.getApartment(), address.getIndex());
        if (duplicate == null) {
            return addressRepository.saveAndFlush(address);
        } else {
            throw new DuplicateDataException("В базе уже зарегестрирован такой адрес.");
        }
    }

    @Override
    public Address update(Address address) {
        Address duplicate = addressRepository.findByRegionAndCityAndStreetAndBuildingAndApartmentAndIndex(address.getRegion(),
                address.getCity(), address.getStreet(), address.getBuilding(), address.getApartment(), address.getIndex());
        if (duplicate == null) {
            return addressRepository.saveAndFlush(address);
        } else {
            throw new DuplicateDataException("В базе уже зарегестрирован такой адрес.");
        }
    }

    @Override
    public void delete(long id) {
        addressRepository.delete(id);
    }

    @Override
    public void delete(Address address) {
        addressRepository.delete(address);
    }

    @Override
    public Optional<Address> findById(long id) {
        return Optional.ofNullable(addressRepository.getOne(id));
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

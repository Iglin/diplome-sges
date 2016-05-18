package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ssk.model.Address;

import java.util.List;

/**
 * Created by user on 15.05.2016.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor {
    List<Address> findByRegion(String region);
    List<Address> findByCity(String city);
    List<Address> findByStreet(String street);
    List<Address> findByBuilding(String building);
    List<Address> findByApartment(String apartment);
    List<Address> findByIndex(int index);
    List<Address> findByRegionAndCityAndStreetAndBuildingAndApartmentAndIndex(
            String region,
            String city,
            String street,
            String building,
            String apartment,
            int index);
    @Modifying
    @Query("delete from Address a where a.id in ?1")
    void deleteAddressesWithIds(List<Long> ids);
}

package ru.ssk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by user on 15.05.2016.
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {
    @Id
    @SequenceGenerator(name = "address_seq", sequenceName = "address_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "region", nullable = false, unique = false)
    private String region;
    @Column(name = "city", nullable = false, unique = false)
    private String city;
    @Column(name = "street", nullable = false, unique = false)
    private String street;
    @Column(name = "building", nullable = false, unique = false)
    private String building;
    @Column(name = "apartment", nullable = true, unique = false)
    private String apartment;
    @Column(name = "index", nullable = false, unique = false)
    private int index;

    @OneToMany(mappedBy = "registrationAddress", fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JsonIgnore
    private Set<Passport> passports;
    @OneToMany(mappedBy = "livingAddress", fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JsonIgnore
    private Set<PhysicalPerson> persons;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Set<Passport> getPassports() {
        return passports;
    }

    public void setPassports(Set<Passport> passports) {
        this.passports = passports;
    }

    public Set<PhysicalPerson> getPersons() {
        return persons;
    }

    public void setPersons(Set<PhysicalPerson> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return region + ", " + city + ", " + street + ", " + building + ", " + apartment + "; Индекс: " + index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (index != address.index) return false;
        if (id != null ? !id.equals(address.id) : address.id != null) return false;
        if (!region.equals(address.region)) return false;
        if (!city.equals(address.city)) return false;
        if (!street.equals(address.street)) return false;
        if (!building.equals(address.building)) return false;
        if (apartment != null ? !apartment.equals(address.apartment) : address.apartment != null) return false;
        if (passports != null ? !passports.equals(address.passports) : address.passports != null) return false;
        return persons != null ? persons.equals(address.persons) : address.persons == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + region.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + building.hashCode();
        result = 31 * result + (apartment != null ? apartment.hashCode() : 0);
        result = 31 * result + index;
        result = 31 * result + (passports != null ? passports.hashCode() : 0);
        result = 31 * result + (persons != null ? persons.hashCode() : 0);
        return result;
    }
}

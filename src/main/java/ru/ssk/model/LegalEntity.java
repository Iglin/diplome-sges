package ru.ssk.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by user on 23.05.2016.
 */
@Entity
@Table(name = "legal_entity")
public class LegalEntity extends Owner {
    //@Id
    @Column(name = "ogrn", nullable = false, unique = true)
    private String ogrn;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "inn", nullable = false)
    private String inn;
    @Column(name = "kpp", nullable = false)
    private String kpp;
    @Column(name = "reg_date", nullable = false)
    private Date registrationDate;
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "address", nullable = false)
    private Address address;

    public LegalEntity() {
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegalEntity that = (LegalEntity) o;

        if (ogrn != null ? !ogrn.equals(that.ogrn) : that.ogrn != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (inn != null ? !inn.equals(that.inn) : that.inn != null) return false;
        if (kpp != null ? !kpp.equals(that.kpp) : that.kpp != null) return false;
        if (registrationDate != null ? !registrationDate.equals(that.registrationDate) : that.registrationDate != null)
            return false;
        return address != null ? address.equals(that.address) : that.address == null;

    }

    @Override
    public int hashCode() {
        int result = ogrn != null ? ogrn.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (inn != null ? inn.hashCode() : 0);
        result = 31 * result + (kpp != null ? kpp.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}

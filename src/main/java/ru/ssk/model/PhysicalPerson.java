package ru.ssk.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by user on 17.05.2016.
 */
@Entity
@Table(name = "person")
public class PhysicalPerson extends Owner implements Serializable {
    @Column(name = "lastname", nullable = false)
    private String lastName;
    @Column(name = "firstname", nullable = false)
    private String firstName;
    @Column(name = "middlename", nullable = true)
    private String middleName;
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })//, fetch = FetchType.EAGER)
    @JoinColumn(name = "living_addr", nullable = false)
    private Address livingAddress;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_num", nullable = false, unique = true)
    private Passport passport;

    public PhysicalPerson() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Address getLivingAddress() {
        return livingAddress;
    }

    public void setLivingAddress(Address livingAddress) {
        this.livingAddress = livingAddress;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhysicalPerson person = (PhysicalPerson) o;

        if (!lastName.equals(person.lastName)) return false;
        if (!firstName.equals(person.firstName)) return false;
        if (middleName != null ? !middleName.equals(person.middleName) : person.middleName != null) return false;
        if (!livingAddress.equals(person.livingAddress)) return false;
        return passport.equals(person.passport);

    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (livingAddress != null ? livingAddress.hashCode() : 0);
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        return result;
    }
}

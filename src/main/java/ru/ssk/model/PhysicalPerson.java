package ru.ssk.model;

import javax.persistence.*;

/**
 * Created by user on 17.05.2016.
 */
@Entity
@Table(name = "person")
public class PhysicalPerson extends Owner {
    @Column(name = "lastname", nullable = false)
    private String lastName;
    @Column(name = "firstname", nullable = false)
    private String firstName;
    @Column(name = "middlename", nullable = false)
    private String middleName;
    @ManyToOne
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
}

package ru.ssk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by user on 17.05.2016.
 */
@Entity
@Table(name = "passport")
public class Passport {
    @Id
    @Column(name = "passport_num", nullable = false, unique = true)
    private Long passportNumber;
    @Column(name = "issue_place", nullable = false)
    private String placeOfIssue;
    @Column(name = "issued_date", nullable = false)
    private Date dateOfIssue;
    @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinColumn(name = "registration", nullable = false)
    private Address registrationAddress;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "passport", fetch = FetchType.LAZY)
    @JsonIgnore
    private PhysicalPerson physicalPerson;


    public Passport() {

    }

    public Passport(Long passportNumber, String placeOfIssue, Date dateOfIssue, Address registrationAddress) {
        this.passportNumber = passportNumber;
        this.placeOfIssue = placeOfIssue;
        this.dateOfIssue = dateOfIssue;
        this.registrationAddress = registrationAddress;
    }

    public Long getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(Long passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Address getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(Address registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public PhysicalPerson getPhysicalPerson() {
        return physicalPerson;
    }

    public void setPhysicalPerson(PhysicalPerson physicalPerson) {
        this.physicalPerson = physicalPerson;
    }
}

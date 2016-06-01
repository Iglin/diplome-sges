package ru.ssk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by user on 25.05.2016.
 */
@Entity
@Table(name = "metering_point")
public class MeteringPoint {
    @Id
    @SequenceGenerator(name = "metering_point_seq", sequenceName = "metering_point_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metering_point_seq")
    private Long id;
    @Column(name = "install_date", nullable = true)
    private Date installationDate;
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "address", unique = true, nullable = false)
    private Address address;
    @ManyToOne(//cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", nullable = false)
    private Owner owner;
    @ManyToOne(//cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "enterprise", nullable = false)
    private Enterprise enterpriseEntry;
    @ManyToOne(//cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "meter", nullable = true, unique = true)
    private Meter meter;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "meteringPoint", fetch = FetchType.LAZY)
    @JsonIgnore
    private EntityStatement entityStatement;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "meteringPoint", fetch = FetchType.LAZY)
    @JsonIgnore
    private Agreement agreement;

    public MeteringPoint() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Enterprise getEnterpriseEntry() {
        return enterpriseEntry;
    }

    public void setEnterpriseEntry(Enterprise enterpriseEntry) {
        this.enterpriseEntry = enterpriseEntry;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public EntityStatement getEntityStatement() {
        return entityStatement;
    }

    public void setEntityStatement(EntityStatement entityStatement) {
        this.entityStatement = entityStatement;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }
}

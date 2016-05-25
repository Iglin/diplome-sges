package ru.ssk.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
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
    @Column(name = "install_date", nullable = false)
    private Date installationDate;
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "address", nullable = false)
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
    @JoinColumn(name = "meter", nullable = false, unique = true)
    private Meter meter;

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
}

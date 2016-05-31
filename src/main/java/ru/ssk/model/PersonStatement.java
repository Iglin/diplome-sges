package ru.ssk.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by user on 31.05.2016.
 */
@Entity
@Table(name = "person_statement")
public class PersonStatement {
    @Id
    @Column(name = "stat_num")
    private Long number;
    @Column(name = "rooms_count", nullable = false)
    private int roomsCount;
    @Column(name = "registered_count", nullable = false)
    private int registeredCount;
    @Column(name = "living_count", nullable = false)
    private int livingCount;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "elevator", nullable = false)
    private boolean hasElevator;
    @Column(name = "home_phone", unique = true)
    private String homePhone;
    @OneToOne(fetch = FetchType.EAGER)//, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "metering_point", nullable = false, unique = true)
    private MeteringPoint meteringPoint;

    public PersonStatement() {
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }

    public int getRegisteredCount() {
        return registeredCount;
    }

    public void setRegisteredCount(int registeredCount) {
        this.registeredCount = registeredCount;
    }

    public int getLivingCount() {
        return livingCount;
    }

    public void setLivingCount(int livingCount) {
        this.livingCount = livingCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isHasElevator() {
        return hasElevator;
    }

    public void setHasElevator(boolean hasElevator) {
        this.hasElevator = hasElevator;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public MeteringPoint getMeteringPoint() {
        return meteringPoint;
    }

    public void setMeteringPoint(MeteringPoint meteringPoint) {
        this.meteringPoint = meteringPoint;
    }
}

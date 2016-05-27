package ru.ssk.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by user on 27.05.2016.
 */
@Entity
@Table(name = "entity_statement")
public class EntityStatement {
    @Id
    @Column(name = "stat_num")
    private Long number;
    @Column(name = "housing", nullable = false)
    private boolean housing;
    @Column(name = "date", nullable = false)
    private Date date;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metering_point", nullable = false, unique = true)
    private MeteringPoint meteringPoint;

    public EntityStatement() {
    }

    public EntityStatement(Long number, boolean housing, Date date, MeteringPoint meteringPoint) {
        this.number = number;
        this.housing = housing;
        this.date = date;
        this.meteringPoint = meteringPoint;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public boolean isHousing() {
        return housing;
    }

    public void setHousing(boolean housing) {
        this.housing = housing;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MeteringPoint getMeteringPoint() {
        return meteringPoint;
    }

    public void setMeteringPoint(MeteringPoint meteringPoint) {
        this.meteringPoint = meteringPoint;
    }
}

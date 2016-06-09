package ru.ssk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

/**
 * Created by user on 01.06.2016.
 */
@Entity
@Table(name = "agreement")
public class Agreement implements Serializable {
    @Id
    @Column(name = "agreement_num", unique = true)
    private Long number;
    @Column(name = "total", nullable = false)
    private BigDecimal total;
    @Column(name = "date", nullable = false)
    private Date date;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "point", nullable = false, unique = true)
    private MeteringPoint meteringPoint;
    @OneToMany(mappedBy = "agreement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ServiceInAgreement> services;

    private static final double VAD = 0.18;

    public Agreement() {
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public Set<ServiceInAgreement> getServices() {
        return services;
    }

    public void setServices(Set<ServiceInAgreement> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agreement agreement = (Agreement) o;

        if (number != null ? !number.equals(agreement.number) : agreement.number != null)
            return false;
        if (total != null ? !total.equals(agreement.total) : agreement.total != null) return false;
        if (date != null ? !date.equals(agreement.date) : agreement.date != null) return false;
        return meteringPoint != null ? meteringPoint.equals(agreement.meteringPoint) : agreement.meteringPoint == null;

    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (meteringPoint != null ? meteringPoint.hashCode() : 0);
        return result;
    }

    public BigDecimal calculateTotal() {
        total = new BigDecimal(0);
        services.forEach(serviceInAgreement -> {
            total = total.add(BigDecimal.valueOf(serviceInAgreement.getCount() * serviceInAgreement.getCoefficient())
                            .multiply(serviceInAgreement.getExtraService().getPrice()));
        });
        total = total.multiply(BigDecimal.valueOf(1 + VAD));
        return total;
    }
}

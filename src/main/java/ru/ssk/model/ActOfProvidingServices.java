package ru.ssk.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by user on 08.06.2016.
 */
@Entity
@Table(name = "act_services")
public class ActOfProvidingServices {
    @Id
    @Column(name = "act_num", unique = true)
    private Long number;
    @Column(name = "date", nullable = false)
    private Date date;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_num", nullable = false, unique = true)
    private Agreement agreement;

    public ActOfProvidingServices() {
    }

    public ActOfProvidingServices(Long number, Date date, Agreement agreement) {
        this.number = number;
        this.date = date;
        this.agreement = agreement;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActOfProvidingServices that = (ActOfProvidingServices) o;

        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}

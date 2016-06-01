package ru.ssk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by user on 01.06.2016.
 */
@Entity
@Table(name = "service_in_agreement")
@IdClass(ServiceInAgreement.ServicePK.class)
public class ServiceInAgreement {
    public static class ServicePK implements Serializable {
        protected ExtraService extraService;
        protected Agreement agreement;

        public ServicePK() {}

        public ServicePK(ExtraService extraService, Agreement agreement) {
            this.extraService = extraService;
            this.agreement = agreement;
        }

        public ExtraService getExtraService() {
            return extraService;
        }

        public void setExtraService(ExtraService extraService) {
            this.extraService = extraService;
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

            ServicePK servicePK = (ServicePK) o;

            if (extraService != null ? !extraService.equals(servicePK.extraService) : servicePK.extraService != null)
                return false;
            return agreement != null ? agreement.equals(servicePK.agreement) : servicePK.agreement == null;

        }

        @Override
        public int hashCode() {
            int result = extraService != null ? extraService.hashCode() : 0;
            result = 31 * result + (agreement != null ? agreement.hashCode() : 0);
            return result;
        }
    }

    @Id
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "extra_service", unique = false, nullable = false)
    private ExtraService extraService;
    @Id
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_num", unique = false, nullable = false)
    @JsonIgnore
    private Agreement agreement;

    @Column(name = "count", nullable = false)
    private int count;
    @Column(name = "coefficient", nullable = false)
    private double coefficient;

    public ServiceInAgreement() {
    }

    public ExtraService getExtraService() {
        return extraService;
    }

    public void setExtraService(ExtraService extraService) {
        this.extraService = extraService;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}

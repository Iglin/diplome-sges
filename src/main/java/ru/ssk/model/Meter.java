package ru.ssk.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by user on 24.05.2016.
 */
@Entity
@Table(name = "meter")
public class Meter {
    @Id
    @SequenceGenerator(name = "meter_seq", sequenceName = "meter_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meter_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;
    @Column(name = "production_year", nullable = false)
    private int productionYear;
    @Column(name = "last_calibr_date", nullable = true)
    private Date lastCalibrationDate;
    @Column(name = "start_readout", nullable = false)
    private double startingReadout;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model", nullable = false)
    private MeterModel model;

    public Meter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public Date getLastCalibrationDate() {
        return lastCalibrationDate;
    }

    public void setLastCalibrationDate(Date lastCalibrationDate) {
        this.lastCalibrationDate = lastCalibrationDate;
    }

    public double getStartingReadout() {
        return startingReadout;
    }

    public void setStartingReadout(double startingReadout) {
        this.startingReadout = startingReadout;
    }

    public MeterModel getModel() {
        return model;
    }

    public void setModel(MeterModel model) {
        this.model = model;
    }
}

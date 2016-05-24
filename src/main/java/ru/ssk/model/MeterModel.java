package ru.ssk.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by user on 24.05.2016.
 */
@Entity
@Table(name = "meter_model")
public class MeterModel {
    @Id
    @SequenceGenerator(name = "meter_model_seq", sequenceName = "meter_model_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meter_model_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "direct_power", nullable = false)
    private boolean directPower;
    @Column(name = "three_phase", nullable = false)
    private boolean threePhase;
    @Column(name = "calibration_period", nullable = false)
    private int calibrationPeriod;  // years
    @Column(name = "electronic", nullable = false)
    private boolean electronic;
    @Column(name = "rates_count", nullable = false)
    private int ratesCount;
    @Column(name = "interface", nullable = false)
    private String meterInterface;
    @Column(name = "atomicity", nullable = false)
    private int atomicity;
    @Column(name = "accuracy", nullable = false)
    private String accuracy;
    @Column(name = "i_nominal", nullable = false)
    private int nominalCurrent;
    @Column(name = "i_maximal", nullable = false)
    private int maximalCurrent;
    @Column(name = "u_nominal", nullable = false)
    private int nominalVoltage;

    public MeterModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isDirectPower() {
        return directPower;
    }

    public void setDirectPower(boolean directPower) {
        this.directPower = directPower;
    }

    public boolean isThreePhase() {
        return threePhase;
    }

    public void setThreePhase(boolean threePhase) {
        this.threePhase = threePhase;
    }

    public int getCalibrationPeriod() {
        return calibrationPeriod;
    }

    public void setCalibrationPeriod(int calibrationPeriod) {
        this.calibrationPeriod = calibrationPeriod;
    }

    public boolean isElectronic() {
        return electronic;
    }

    public void setElectronic(boolean electronic) {
        this.electronic = electronic;
    }

    public int getRatesCount() {
        return ratesCount;
    }

    public void setRatesCount(int ratesCount) {
        this.ratesCount = ratesCount;
    }

    public String getMeterInterface() {
        return meterInterface;
    }

    public void setMeterInterface(String meterInterface) {
        this.meterInterface = meterInterface;
    }

    public int getAtomicity() {
        return atomicity;
    }

    public void setAtomicity(int atomicity) {
        this.atomicity = atomicity;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public int getNominalCurrent() {
        return nominalCurrent;
    }

    public void setNominalCurrent(int nominalCurrent) {
        this.nominalCurrent = nominalCurrent;
    }

    public int getMaximalCurrent() {
        return maximalCurrent;
    }

    public void setMaximalCurrent(int maximalCurrent) {
        this.maximalCurrent = maximalCurrent;
    }

    public int getNominalVoltage() {
        return nominalVoltage;
    }

    public void setNominalVoltage(int nominalVoltage) {
        this.nominalVoltage = nominalVoltage;
    }
}

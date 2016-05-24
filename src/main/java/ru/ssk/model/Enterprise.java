package ru.ssk.model;

import javax.persistence.*;

/**
 * Created by user on 24.05.2016.
 */
@Entity
@Table(name = "enterprise")
public class Enterprise {
    @Id
    @SequenceGenerator(name = "enterprise_seq", sequenceName = "enterprise_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enterprise_seq")
    private Long id;
    @Column(name = "actual", nullable = false)
    private boolean actual;
    @Column(name = "director", nullable = false)
    private String director;
    @Column(name = "registry_chief", nullable = false)
    private String registryChief;
    @Column(name = "k_s", nullable = false)
    private String correspondentAccount;
    @Column(name = "r_s", nullable = false)
    private String checkingAccount;
    @Column(name = "bank", nullable = false)
    private String bank;
    @Column(name = "bank_inn", nullable = false)
    private String bankInn;
    @Column(name = "bank_kpp", nullable = false)
    private String bankKpp;
    @Column(name = "bank_bik", nullable = false)
    private String bankBik;
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_addr", nullable = false)
    private Address bankAddress;

    public Enterprise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRegistryChief() {
        return registryChief;
    }

    public void setRegistryChief(String registryChief) {
        this.registryChief = registryChief;
    }

    public String getCorrespondentAccount() {
        return correspondentAccount;
    }

    public void setCorrespondentAccount(String correspondentAccount) {
        this.correspondentAccount = correspondentAccount;
    }

    public String getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(String checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankInn() {
        return bankInn;
    }

    public void setBankInn(String bankInn) {
        this.bankInn = bankInn;
    }

    public String getBankKpp() {
        return bankKpp;
    }

    public void setBankKpp(String bankKpp) {
        this.bankKpp = bankKpp;
    }

    public String getBankBik() {
        return bankBik;
    }

    public void setBankBik(String bankBik) {
        this.bankBik = bankBik;
    }

    public Address getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(Address bankAddress) {
        this.bankAddress = bankAddress;
    }
}

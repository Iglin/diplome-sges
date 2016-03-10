package ru.aosges.model;

import javax.persistence.*;

/**
 * Created by user on 10.03.2016.
 */
@Entity
@Table(name = "owner")
public class Owner {
    @Id
    @SequenceGenerator(name = "owner_seq", sequenceName = "owner_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_seq")
    @Column(name = "id")
    private Integer id;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "e_mail", nullable = true)
    private String email;
    @Column(name = "personal_acc", nullable = false)
    private String personalAccount;

    public Owner() {
    }

    public Owner(String phone, String email, String personalAccount) {
        this.phone = phone;
        this.email = email;
        this.personalAccount = personalAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(String personalAccount) {
        this.personalAccount = personalAccount;
    }
}

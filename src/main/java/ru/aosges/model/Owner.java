package ru.aosges.model;

import javax.persistence.*;

/**
 * Created by user on 10.03.2016.
 */
@Entity
@Table(name = "owner")//, uniqueConstraints = @UniqueConstraint(columnNames = { "phone", "e_mail", "personal_acc"}))
public class Owner {
    @Id
    @SequenceGenerator(name = "owner_seq", sequenceName = "owner_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    @Column(name = "e_mail", nullable = true, unique = true)
    private String email;
    @Column(name = "personal_acc", nullable = false, unique = true)
    private Long personalAccount;

    public Owner() {
    }

    public Owner(String phone, String email, long personalAccount) {
        this.phone = phone;
        this.email = email;
        this.personalAccount = personalAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(long personalAccount) {
        this.personalAccount = personalAccount;
    }
}

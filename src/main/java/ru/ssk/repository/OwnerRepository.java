package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ssk.model.Owner;

import java.util.List;

/**
 * Created by root on 13.03.16.
 */
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("SELECT o FROM Owner o WHERE o.personalAccount = :personalAccount")
    Owner findByPersonalAccount(@Param("personalAccount") long personalAccount);

    @Query("SELECT o FROM Owner o WHERE o.phone = :phone")
    Owner findByPhone(@Param("phone") String phone);

    @Query("SELECT o FROM Owner o WHERE o.email = :email")
    Owner findByEmail(@Param("email") String email);

    @Modifying
    @Query("delete from Owner a where a.id in ?1")
    void deleteOwnersWithIds(List<Long> ids);
}
package ru.aosges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.aosges.model.Owner;

/**
 * Created by root on 13.03.16.
 */
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("SELECT o FROM Owner o WHERE o.personalAccount = :personalAccount")
    Owner findByPersonalAccount(@Param("personalAccount") long personalAccount);
}
package ru.aosges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aosges.model.Owner;

/**
 * Created by root on 13.03.16.
 */
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("SELECT Owner FROM Owner WHERE personalAccount = :acc")
    Owner findByPersonalAccount(@Param("personalAccount") long acc);
}

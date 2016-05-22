package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.Passport;

import java.util.List;

/**
 * Created by root on 18.05.16.
 */
@Repository
@Transactional
public interface PassportRepository extends JpaRepository<Passport, Long>, JpaSpecificationExecutor {
    @Query("SELECT p FROM Passport p WHERE p.physicalPerson.id = :id")
    Passport findByPersonId(@Param("id") long id);

    @Query("SELECT p FROM Passport p WHERE p.physicalPerson.id in ?1")
    List<Passport> findByPersonsIds(@Param("ids") List<Long> ids);

    @Modifying
    @Query("DELETE FROM Passport p WHERE p.physicalPerson.id in ?1")
    void deleteWithOwnersIds(@Param("ids") List<Long> ids);
}

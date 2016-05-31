package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.MeteringPoint;
import ru.ssk.model.PersonStatement;

import java.sql.Date;
import java.util.List;

/**
 * Created by user on 31.05.2016.
 */
@Repository
@Transactional
public interface PersonStatementRepository  extends JpaRepository<PersonStatement, Long> {
    PersonStatement findByNumber(long number);
    PersonStatement findByMeteringPoint(MeteringPoint meteringPoint);
    List<PersonStatement> findByDate(Date date);
    List<PersonStatement> findByHasElevator(boolean hasElevator);

    @Modifying
    @Query("DELETE FROM PersonStatement a WHERE a.number IN ?1")
    void deleteWithIds(List<Long> ids);
}

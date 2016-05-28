package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.EntityStatement;
import ru.ssk.model.MeteringPoint;

import java.sql.Date;
import java.util.List;

/**
 * Created by user on 28.05.2016.
 */
@Repository
@Transactional
public interface EntityStatementRepository extends JpaRepository<EntityStatement, Long> {
    EntityStatement findByNumber(long number);
    EntityStatement findByMeteringPoint(MeteringPoint meteringPoint);
    List<EntityStatement> findByDate(Date date);
    List<EntityStatement> findByHousing(boolean housing);

    @Modifying
    @Query("DELETE FROM EntityStatement a WHERE a.number IN ?1")
    void deleteWithIds(List<Long> ids);
}

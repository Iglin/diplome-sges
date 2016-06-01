package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.Agreement;
import ru.ssk.model.MeteringPoint;

import java.util.List;

/**
 * Created by user on 01.06.2016.
 */
@Repository
@Transactional
public interface AgreementRepository extends JpaRepository<Agreement, Long> {
    Agreement findByMeteringPoint(MeteringPoint meteringPoint);

    @Modifying
    @Query("delete from Agreement a where a.number in ?1")
    void deleteWithIds(List<Long> ids);
}

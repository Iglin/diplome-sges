package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.Enterprise;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
@Repository
@Transactional
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
    List<Enterprise> findByDirector(String director);
    List<Enterprise> findByRegistryChief(String registryChief);
    @Query("SELECT e FROM Enterprise e WHERE e.actual = true")
    Enterprise findActual();

    @Modifying
    @Query("UPDATE Enterprise e SET e.actual = false")
    void clearActuals();

    @Modifying
    @Query("UPDATE Enterprise e SET e.actual = true WHERE e.id = ?1")
    void setActual(long id);

    @Modifying
    @Query("delete from Enterprise a where a.id in ?1")
    void deleteWithIds(List<Long> ids);
}

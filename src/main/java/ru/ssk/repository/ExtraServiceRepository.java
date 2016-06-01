package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.ExtraService;

import java.util.List;

/**
 * Created by user on 01.06.2016.
 */
@Repository
@Transactional
public interface ExtraServiceRepository extends JpaRepository<ExtraService, Long> {
    List<ExtraService> findByName(String name);

    @Modifying
    @Query("delete from ExtraService a where a.id in ?1")
    void deleteWithIds(List<Long> ids);
}

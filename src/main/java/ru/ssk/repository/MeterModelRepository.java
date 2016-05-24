package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.MeterModel;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
@Repository
@Transactional
public interface MeterModelRepository extends JpaRepository<MeterModel, Long> {
    List<MeterModel> findByName(String name);
    List<MeterModel> findByManufacturer(String manufacturer);
    List<MeterModel> findByNameAndManufacturer(String name, String manufacturer);

    @Modifying
    @Query("DELETE FROM MeterModel a WHERE a.id IN ?1")
    void deleteWithIds(List<Long> ids);
}

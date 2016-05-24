package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.Meter;
import ru.ssk.model.MeterModel;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
@Repository
@Transactional
public interface MeterRepository extends JpaRepository<Meter, Long> {
    List<Meter> findBySerialNumber(String serialNumber);
    List<Meter> findByProductionYear(int productionYear);
    List<Meter> findByModel(MeterModel model);

    @Modifying
    @Query("DELETE FROM Meter a WHERE a.id IN ?1")
    void deleteWithIds(List<Long> ids);
}

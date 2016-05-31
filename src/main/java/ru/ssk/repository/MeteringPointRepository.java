package ru.ssk.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by user on 25.05.2016.
 */
@Repository
@Transactional
public interface MeteringPointRepository extends JpaRepository<MeteringPoint, Long>, JpaSpecificationExecutor {
    List<MeteringPoint> findByInstallationDate(Date date);
    List<MeteringPoint> findByMeter(Meter meter);
    List<MeteringPoint> findByOwner(Owner owner);
    List<MeteringPoint> findByAddress(Address address);
    List<MeteringPoint> findByEnterpriseEntry(Enterprise enterprise);

    @Query("SELECT e FROM MeteringPoint e WHERE e.owner.id IN (SELECT o.id FROM LegalEntity o)")
    List<MeteringPoint> findAllEntityPoints();

    @Query("SELECT e FROM MeteringPoint e WHERE e.owner.id IN (SELECT o.id FROM PhysicalPerson o)")
    List<MeteringPoint> findAllPersonPoints();

    @Modifying
    @Query("DELETE FROM MeteringPoint a WHERE a.id IN ?1")
    void deleteWithIds(List<Long> ids);
}

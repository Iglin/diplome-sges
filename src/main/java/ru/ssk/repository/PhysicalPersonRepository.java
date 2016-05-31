package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.Address;
import ru.ssk.model.Passport;
import ru.ssk.model.PhysicalPerson;

import java.util.List;

/**
 * Created by root on 18.05.16.
 */
@Repository
@Transactional
public interface PhysicalPersonRepository extends JpaRepository<PhysicalPerson, Long>, JpaSpecificationExecutor {
    PhysicalPerson findById(Long id);
    PhysicalPerson findByPersonalAccount(Long personalAccount);
    PhysicalPerson findByEmail(String email);
    PhysicalPerson findByPhone(String phone);

    List<PhysicalPerson> findByLivingAddress(Address address);
    PhysicalPerson findByPassport(Passport passport);
    List<PhysicalPerson> findByLastName(String lastName);
    List<PhysicalPerson> findByFirstName(String firstName);
    List<PhysicalPerson> findByMiddleName(String middleName);
    List<PhysicalPerson> findByLastNameAndFirstNameAndMiddleName(String lastname, String firstName, String middleName);

    @Query("SELECT o FROM PhysicalPerson o WHERE o.passport.passportNumber = :passportNumber")
    PhysicalPerson findByPassportNumber(long passportNumber);

    @Modifying
    @Query("delete from PhysicalPerson a where a.id in ?1")
    void deleteWithIds(List<Long> ids);
}

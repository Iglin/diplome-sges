package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.Agreement;
import ru.ssk.model.Receipt;

import java.util.List;

/**
 * Created by user on 10.06.2016.
 */
@Repository
@Transactional
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Receipt findByAgreement(Agreement agreement);

    @Query("SELECT a FROM Receipt a where a.agreement.number = ?1")
    Receipt findByAgreementNumber(long number);

    @Modifying
    @Query("DELETE FROM Receipt s WHERE s.agreement.number IN ?1")
    void deleteWithAgreementNumbers(List<Long> numbers);

    @Modifying
    @Query("delete from Receipt a where a.number in ?1")
    void deleteWithIds(List<Long> ids);
}

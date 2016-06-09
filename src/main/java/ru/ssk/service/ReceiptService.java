package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.Agreement;
import ru.ssk.model.Receipt;

import java.util.List;

/**
 * Created by user on 10.06.2016.
 */
@Service
public interface ReceiptService {
    Receipt save(Receipt receipt);
    void delete(long number);
    void delete(Receipt receipt);
    void deleteWithIds(List<Long> ids);
    void deleteWithAgreementNumbers(List<Long> numbers);
    Receipt findByNumber(long number);
    Receipt findByAgreement(Agreement agreement);
    Receipt findByAgreementNumber(long number);
    List<Receipt> findAll();
}

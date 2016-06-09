package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.Agreement;
import ru.ssk.model.Receipt;
import ru.ssk.repository.ReceiptRepository;
import ru.ssk.service.ReceiptService;

import java.util.List;

/**
 * Created by user on 10.06.2016.
 */
public class ReceiptServiceImpl implements ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public Receipt save(Receipt receipt) {
        return receiptRepository.saveAndFlush(receipt);
    }

    @Override
    public void delete(long number) {
        receiptRepository.delete(number);
        receiptRepository.flush();
    }

    @Override
    public void delete(Receipt receipt) {
        receiptRepository.delete(receipt);
        receiptRepository.flush();
    }

    @Override
    public void deleteWithAgreementNumbers(List<Long> numbers) {
        receiptRepository.deleteWithAgreementNumbers(numbers);
        receiptRepository.flush();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        receiptRepository.deleteWithIds(ids);
        receiptRepository.flush();
    }

    @Override
    public Receipt findByNumber(long number) {
        return receiptRepository.findOne(number);
    }

    @Override
    public Receipt findByAgreement(Agreement agreement) {
        return receiptRepository.findByAgreement(agreement);
    }

    @Override
    public Receipt findByAgreementNumber(long number) {
        return receiptRepository.findByAgreementNumber(number);
    }

    @Override
    public List<Receipt> findAll() {
        return receiptRepository.findAll();
    }
}

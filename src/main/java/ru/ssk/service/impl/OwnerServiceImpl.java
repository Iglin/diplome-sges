package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.ssk.exception.UniqueViolationException;
import ru.ssk.model.Owner;
import ru.ssk.repository.OwnerRepository;
import ru.ssk.service.OwnerService;

import java.util.List;

/**
 * Created by root on 13.03.16.
 */
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public Owner save(Owner owner) {
        try {
            return ownerRepository.saveAndFlush(owner);
        } catch (DataIntegrityViolationException e) {
            if (ownerRepository.findByEmail(owner.getEmail()) != null) {
                throw new UniqueViolationException("В базе уже есть собственник с таким e-mail адресом.");
            } else if (ownerRepository.findByPersonalAccount(owner.getPersonalAccount()) != null) {
                throw new UniqueViolationException("В базе уже есть собственник с таким номером счёта.");
            } else if (ownerRepository.findByPhone(owner.getPhone()) != null) {
                throw new UniqueViolationException("В базе уже есть собственник с таким номером телефона.");
            } else {
                throw new UniqueViolationException("Нарушено ограничение при добавлении записи в базу.");
            }
        }
    }

    @Override
    public Owner findById(long id) {
        return ownerRepository.findOne(id);
    }

    @Override
    public void delete(long id) {
        ownerRepository.delete(id);
        ownerRepository.flush();
    }

    @Override
    public void delete(Owner owner) {
        ownerRepository.delete(owner);
        ownerRepository.flush();
    }

    @Override
    public void deleteOwnersWithIds(List<Long> ids) {
        ownerRepository.deleteOwnersWithIds(ids);
    }

    @Override
    public Owner findByPersonalAccount(long personalAccount) {
        return ownerRepository.findByPersonalAccount(personalAccount);
    }

    @Override
    public Owner findByPhone(String phone) {
        return ownerRepository.findByPhone(phone);
    }

    @Override
    public Owner findByEmail(String email) {
        return ownerRepository.findByEmail(email);
    }

    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }
}

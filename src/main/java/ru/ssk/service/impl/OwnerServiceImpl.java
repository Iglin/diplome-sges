package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.ssk.exception.UniqueViolationException;
import ru.ssk.model.Owner;
import ru.ssk.repository.OwnerRepository;
import ru.ssk.service.OwnerService;

import java.util.List;
import java.util.Optional;

/**
 * Created by root on 13.03.16.
 */
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    private void resolveConstraintViolation(DataIntegrityViolationException e, Owner owner) {
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

    @Override
    public Owner add(Owner owner) {
        try {
            return ownerRepository.saveAndFlush(owner);
        } catch (DataIntegrityViolationException e) {
            resolveConstraintViolation(e, owner);
            return null;
        }
    }

    @Override
    public Optional<Owner> findById(long id) {
        return Optional.ofNullable(ownerRepository.getOne(id));
    }

    @Override
    public void delete(long id) {
        ownerRepository.delete(id);
    }

    @Override
    public void delete(Owner owner) {
        ownerRepository.delete(owner);
    }

    @Override
    public Optional<Owner> findByPersonalAccount(long personalAccount) {
        return Optional.ofNullable(ownerRepository.findByPersonalAccount(personalAccount));
    }

    @Override
    public Optional<Owner> findByPhone(String phone) {
        return Optional.ofNullable(ownerRepository.findByPhone(phone));
    }

    @Override
    public Optional<Owner> findByEmail(String email) {
        return Optional.ofNullable(ownerRepository.findByEmail(email));
    }

    @Override
    public Owner update(Owner owner) {
        try {
            return ownerRepository.saveAndFlush(owner);
        } catch (DataIntegrityViolationException e) {
            resolveConstraintViolation(e, owner);
            return null;
        }
    }

    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }
}

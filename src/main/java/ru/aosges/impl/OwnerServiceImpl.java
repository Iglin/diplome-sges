package ru.aosges.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.aosges.model.Owner;
import ru.aosges.repository.OwnerRepository;
import ru.aosges.service.OwnerService;

import java.util.List;

/**
 * Created by root on 13.03.16.
 */
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public Owner add(Owner owner) {
        return ownerRepository.saveAndFlush(owner);
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
    public Owner getByPersonalAccount(long personalAccount) {
        return ownerRepository.findByPersonalAccount(personalAccount);
    }

    @Override
    public Owner update(Owner owner) {
        return ownerRepository.saveAndFlush(owner);
    }

    @Override
    public List<Owner> listAll() {
        return ownerRepository.findAll();
    }
}

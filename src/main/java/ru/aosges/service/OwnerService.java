package ru.aosges.service;

import org.springframework.stereotype.Service;
import ru.aosges.model.Owner;

import java.util.List;

/**
 * Created by root on 13.03.16.
 */
@Service
public interface OwnerService {
    Owner add(Owner owner);
    void delete(long id);
    void delete(Owner owner);
    Owner getByPersonalAccount(long personalAccount);
    Owner update(Owner bank);
    List<Owner> listAll();
}

package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.Owner;

import java.util.List;
import java.util.Optional;

/**
 * Created by root on 13.03.16.
 */
@Service
public interface OwnerService {
    Owner save(Owner owner);
    void delete(long id);
    void delete(Owner owner);
    Owner findById(long id);
    Owner findByPersonalAccount(long personalAccount);
    Owner findByPhone(String phone);
    Owner findByEmail(String email);
    List<Owner> findAll();
}

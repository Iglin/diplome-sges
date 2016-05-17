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
    Owner add(Owner owner);
    void delete(long id);
    void delete(Owner owner);
    Optional<Owner> findById(long id);
    Optional<Owner> findByPersonalAccount(long personalAccount);
    Optional<Owner> findByPhone(String phone);
    Optional<Owner> findByEmail(String email);
    Owner update(Owner owner);
    List<Owner> findAll();
}

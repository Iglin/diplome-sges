package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.Owner;

import java.util.List;

/**
 * Created by root on 13.03.16.
 */
@Service
public interface OwnerService {
    Owner save(Owner owner);
    void delete(long id);
    void delete(Owner owner);
    void deleteOwnersWithIds(List<Long> ids);
    Owner findById(long id);
    Owner findByPersonalAccount(long personalAccount);
    Owner findByPhone(String phone);
    Owner findByEmail(String email);
    List<Owner> findAll();
}

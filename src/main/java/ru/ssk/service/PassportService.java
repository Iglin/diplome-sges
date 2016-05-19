package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.Passport;

import java.util.List;

/**
 * Created by user on 19.05.2016.
 */
@Service
public interface PassportService {
    Passport findByPersonId(long id);
    List<Passport> findByPersonsIds(List<Long> ids);
    void delete(Passport passport);
    void deleteWithOwnersIds(List<Long> ids);
}

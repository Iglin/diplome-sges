package ru.ssk.service;

import org.springframework.stereotype.Service;
import ru.ssk.model.User;

import java.util.List;

/**
 * Created by user on 23.05.2016.
 */
@Service
public interface UserService {
    User save(User user);
    void delete(long id);
    void delete(User user);
    User findById(long id);
    User findByLogin(String login);
    List<User> findByRole(String role);
    List<User> findAll();
}

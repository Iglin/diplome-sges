package ru.ssk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.User;
import ru.ssk.repository.UserRepository;
import ru.ssk.service.UserService;

import java.util.List;

/**
 * Created by user on 23.05.2016.
 */
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);
        userRepository.flush();
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
        userRepository.flush();
    }

    @Override
    public User findById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteWithIds(List<Long> ids) {
        userRepository.deleteWithIds(ids);
    }
}

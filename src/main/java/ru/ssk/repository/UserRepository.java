package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssk.model.User;

import java.util.List;

/**
 * Created by user on 23.05.2016.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    List<User> findByRole(String role);

    @Modifying
    @Query("delete from User a where a.id in ?1")
    void deleteWithIds(List<Long> ids);
}

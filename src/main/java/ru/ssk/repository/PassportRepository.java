package ru.ssk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.ssk.model.Passport;

/**
 * Created by root on 18.05.16.
 */
public interface PassportRepository extends JpaRepository<Passport, Long>, JpaSpecificationExecutor {
}

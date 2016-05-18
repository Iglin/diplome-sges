package ru.ssk.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.model.PhysicalPerson;

import static org.junit.Assert.*;

/**
 * Created by root on 18.05.16.
 */
public class PhysicalPersonRepositoryTest {
    @Autowired
    private PhysicalPersonRepository personRepository;

    @Test
    public void findByPersonalAccount() throws Exception {
        PhysicalPerson person = personRepository.findByPersonalAccount(new Long(113322));
        assertEquals(person.getFirstName(), "John");
    }

    @Test
    public void findByEmail() throws Exception {

    }

    @Test
    public void findByPhone() throws Exception {

    }

    @Test
    public void findByLivingAddress() throws Exception {

    }

    @Test
    public void findByPassport() throws Exception {

    }

    @Test
    public void findByLastName() throws Exception {

    }

    @Test
    public void findByFirstName() throws Exception {

    }

    @Test
    public void findByMiddleName() throws Exception {

    }

    @Test
    public void findByLastNameAndFirstNameAndMiddleName() throws Exception {

    }

    @Test
    public void findByPassportNumber() throws Exception {

    }

}
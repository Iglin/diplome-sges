package ru.ssk.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ssk.model.Address;
import ru.ssk.model.Passport;
import ru.ssk.model.PhysicalPerson;
import ru.ssk.repository.AddressRepository;
import ru.ssk.repository.PassportRepository;
import ru.ssk.service.AddressService;
import ru.ssk.service.PhysicalPersonService;

import java.sql.Date;
import java.util.List;

/**
 * Created by root on 18.05.16.
 */
@RestController
@RequestMapping("/test")
@Transactional
public class TestController extends BaseController {
    @Autowired
    private PhysicalPersonService personService;
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Passport test(){
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String fillDataBase(){
        Address address = new Address();
        address.setRegion("Самарская область");
        address.setCity("Самара");
        address.setStreet("Коммунистическая");
        address.setBuilding("2");
        address.setApartment("5");
        address.setIndex(222333);

        Address address2 = new Address();
        address2.setRegion("Самарская область");
        address2.setCity("Самара");
        address2.setStreet("Дачная");
        address2.setBuilding("23");
        address2.setApartment("25");
        address2.setIndex(222355);

        Address address3 = new Address();
        address3.setRegion("Московская область");
        address3.setCity("Москва");
        address3.setStreet("Гагарина");
        address3.setBuilding("221");
        address3.setApartment("221B");
        address3.setIndex(111233);

        Address address4 = new Address();
        address4.setRegion("Самарская область");
        address4.setCity("Чапаевск");
        address4.setStreet("Пионерская");
        address4.setBuilding("3");
        address4.setApartment("5");
        address4.setIndex(222300);

        PhysicalPerson physicalPerson = new PhysicalPerson();
        physicalPerson.setFirstName("Иван");
        physicalPerson.setLastName("Иванов");
        physicalPerson.setMiddleName("Иванович");
        physicalPerson.setEmail("ivan@mail.ru");
        physicalPerson.setPersonalAccount(Long.valueOf(123452));
        physicalPerson.setPhone("+79011111111");
        physicalPerson.setPassport(new Passport(new Long("3422451785"),
                "Отделение УФМС России по Самарской обл. в г. Чапаевске",
                new Date(System.currentTimeMillis()),
                address4));
        physicalPerson.setLivingAddress(address);

        PhysicalPerson physicalPerson1 = new PhysicalPerson();
        physicalPerson1.setFirstName("Петров");
        physicalPerson1.setLastName("Пётр");
        physicalPerson1.setMiddleName("Петрович");
        physicalPerson1.setEmail("pete@mail.ru");
        physicalPerson1.setPersonalAccount(Long.valueOf(123321));
        physicalPerson1.setPhone("+79033311111");
        physicalPerson1.setPassport(new Passport(new Long("3477771785"),
                "Отделение УФМС России в г. Самаре",
                new Date(System.currentTimeMillis()),
                address));
        physicalPerson1.setLivingAddress(address2);

        addressService.save(address3);
        personService.save(physicalPerson);
        personService.save(physicalPerson1);
        return new Gson().toJson("База заполнена начальными значениями.");
    }
}

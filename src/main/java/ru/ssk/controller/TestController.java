package ru.ssk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ssk.model.Address;
import ru.ssk.model.Passport;
import ru.ssk.model.PhysicalPerson;
import ru.ssk.repository.AddressRepository;
import ru.ssk.repository.PassportRepository;
import ru.ssk.service.PhysicalPersonService;

import java.util.List;

/**
 * Created by root on 18.05.16.
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {
    @Autowired
    private PassportRepository passportRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Passport test(){
        return passportRepository.findByPersonId(153);
    }
}

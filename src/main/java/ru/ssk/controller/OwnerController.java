package ru.ssk.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.Owner;
import ru.ssk.service.OwnerService;

import java.util.List;

/**
 * Created by user on 13.05.2016.
 */
@RestController
@RequestMapping("/owners")
public class OwnerController extends BaseController {
    @Autowired
    private OwnerService ownerService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String add(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "personal_acc") long personalAccount) {

        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPhone(phone);
        owner.setPersonalAccount(personalAccount);
        owner = ownerService.save(owner);
        return new Gson().toJson("Собственник зарегестрирован.");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Owner> all(){
        return ownerService.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String update(
            @RequestParam(value = "id") long id,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "personal_acc") long personalAccount) {

        Owner owner = new Owner();
        owner.setId(id);
        owner.setEmail(email);
        owner.setPhone(phone);
        owner.setPersonalAccount(personalAccount);
        ownerService.save(owner);
        return new Gson().toJson("Информация обновлена.");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable(value = "id") int id) {
        ownerService.delete(id);
        return new Gson().toJson("Собственник удалён.");
    }
}

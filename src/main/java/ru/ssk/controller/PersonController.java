package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.exception.MultipleRepresentationsException;
import ru.ssk.model.Address;
import ru.ssk.model.Passport;
import ru.ssk.model.PhysicalPerson;
import ru.ssk.service.AddressService;
import ru.ssk.service.OwnerService;
import ru.ssk.service.PassportService;
import ru.ssk.service.PhysicalPersonService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 18.05.16.
 */
@RestController
@RequestMapping("/persons")
@Transactional
public class PersonController extends BaseController {
    @Autowired
    private PhysicalPersonService personService;
    @Autowired
    private PassportService passportService;
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<PhysicalPerson> allAddresses(){
        return personService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PhysicalPerson getOne(@RequestParam(value = "id") long id){
        PhysicalPerson result = personService.findById(id);
        result.getLivingAddress();
        result.getPassport().getRegistrationAddress();
        return result;
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public String delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            personService.deleteWithIds(Arrays.asList(idsToDelete));
            return new Gson().toJson("Записи успешно удалены.");
        } else {
            return new Gson().toJson("Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/passport/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Passport getPassport(@RequestParam(value = "id") Long personId) {
        return passportService.findByPersonId(personId);
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String updatePerson(@RequestParam(value = "id") long id,
                         @RequestParam(value = "person") String person) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        PhysicalPerson physicalPerson = gson.fromJson(person, PhysicalPerson.class);
        physicalPerson.setId(id);
      //  synchronizeAddressesSession(physicalPerson);
        Address livingAddress = physicalPerson.getLivingAddress();
        Address registrationAddress = physicalPerson.getPassport().getRegistrationAddress();
        System.out.println(physicalPerson.toString());
        System.out.println(physicalPerson.getPassport().toString());
        if (livingAddress.getId() != null && registrationAddress.getId() != null &&
                livingAddress.getId().equals(registrationAddress.getId()) && !livingAddress.equals(registrationAddress)) {
            throw new MultipleRepresentationsException("Невозможно одновременно использовать модифицированную запись об адресе и её старую версию.");
        }
        personService.save(physicalPerson);
        return new Gson().toJson("Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String add(@RequestParam(value = "person") String person) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        PhysicalPerson physicalPerson = gson.fromJson(person, PhysicalPerson.class);
        synchronizeAddressesSession(physicalPerson);
        personService.save(physicalPerson);
        return new Gson().toJson("Данные о физ. лице успешно сохранены в базе.");
    }

    private void synchronizeAddressesSession(PhysicalPerson physicalPerson) {
        Long addressId = physicalPerson.getLivingAddress().getId();
        if (addressId != null) {
            physicalPerson.setLivingAddress(addressService.findById(addressId));
        }
        addressId = physicalPerson.getPassport().getRegistrationAddress().getId();
        if (addressId != null) {
            physicalPerson.getPassport().setRegistrationAddress(addressService.findById(addressId));
        }
    }
}

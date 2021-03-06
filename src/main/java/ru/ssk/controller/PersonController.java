package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.exception.MultipleRepresentationsException;
import ru.ssk.model.Address;
import ru.ssk.model.Passport;
import ru.ssk.model.PhysicalPerson;
import ru.ssk.service.AddressService;
import ru.ssk.service.PassportService;
import ru.ssk.service.PhysicalPersonService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.Specifications.where;
import static ru.ssk.spec.PhysicalPersonSpecs.*;

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
    public List<PhysicalPerson> all(){
        return personService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> openEditor(@RequestParam(value = "id", required = false) String id) {
        Map<String, Object> params = new HashMap<>(2);
        if (id != null) {
            PhysicalPerson result = personService.findById(Long.parseLong(id));
            result.getLivingAddress();
            result.getPassport().getRegistrationAddress();
            params.put("person", result);
        }
        params.put("addresses", addressService.findAll());
        return params;
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            personService.deleteWithIds(Arrays.asList(idsToDelete));
            return new ResponseMessage(true, "Записи успешно удалены.");
        } else {
            return new ResponseMessage(false, "Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/passport/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Passport getPassport(@RequestParam(value = "id") Long personId) {
        return passportService.findByPersonId(personId);
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage updatePerson(@RequestParam(value = "id") long id,
                         @RequestParam(value = "person") String person) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        PhysicalPerson physicalPerson = gson.fromJson(person, PhysicalPerson.class);
        physicalPerson.setId(id);
        Address livingAddress = physicalPerson.getLivingAddress();
        Address registrationAddress = physicalPerson.getPassport().getRegistrationAddress();
        if (livingAddress.getId() != null && registrationAddress.getId() != null && livingAddress.getId().equals(registrationAddress.getId())) {
            if (!livingAddress.equals(registrationAddress)) {
                throw new MultipleRepresentationsException("Невозможно одновременно использовать модифицированную запись об адресе и её старую версию.");
            } else {
                Address newSessionAddress = addressService.findById(livingAddress.getId());
          //      if (!newSessionAddress.equals(livingAddress)) {
                    newSessionAddress.setIndex(livingAddress.getIndex());
                    newSessionAddress.setRegion(livingAddress.getRegion());
                    newSessionAddress.setCity(livingAddress.getCity());
                    newSessionAddress.setStreet(livingAddress.getStreet());
                    newSessionAddress.setBuilding(livingAddress.getBuilding());
                    newSessionAddress.setApartment(livingAddress.getApartment());
                    physicalPerson.setLivingAddress(newSessionAddress);
                    physicalPerson.getPassport().setRegistrationAddress(newSessionAddress);
               // }
            }
        }
        personService.save(physicalPerson);
        return new ResponseMessage(true, "Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage add(@RequestParam(value = "person") String person) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        PhysicalPerson physicalPerson = gson.fromJson(person, PhysicalPerson.class);
        synchronizeAddressesSession(physicalPerson);
        personService.save(physicalPerson);
        return new ResponseMessage(true, "Данные о физ. лице успешно сохранены в базе.");
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

    @RequestMapping(value = "/filter/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public List<PhysicalPerson> filter(@RequestParam(value = "filters") String filters) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        FiltersMap filtersMap = gson.fromJson(filters, FiltersMap.class);
        Map<String, String> filterValues = filtersMap.getFilterValues("Пресональный счёт");
        Specification<PhysicalPerson> specification = null;
        if (filterValues != null) {
            specification = hasPersonalAccount(Long.valueOf(filterValues.get("personalAccount")));
        }
        filterValues = filtersMap.getFilterValues("Адрес");
        if (filterValues != null) {
            String region = filterValues.get("region");
            String street = filterValues.get("street");
            String city = filterValues.get("city");
            String building = filterValues.get("building");
            String apartment = filterValues.get("apartment");
            String index = filterValues.get("index");
            if (specification == null) {
                specification = hasAddress(region, city, street, building, apartment, index);
            } else {
                specification = where(specification).and(hasAddress(region, city, street, building, apartment, index));
            }
        }
        filterValues = filtersMap.getFilterValues("Телефон");
        if (filterValues != null) {
            String phone = filterValues.get("phone");
            if (phone != null && !phone.equals("")) {
                if (specification == null) {
                    specification = hasPhone(phone);
                } else {
                    specification = where(specification).and(hasPhone(phone));
                }
            }
        }
        filterValues = filtersMap.getFilterValues("E-mail");
        if (filterValues != null) {
            String email = filterValues.get("email");
            if (email != null && !email.equals("")) {
                if (specification == null) {
                    specification = hasEmail(email);
                } else {
                    specification = where(specification).and(hasEmail(email));
                }
            }
        }
        filterValues = filtersMap.getFilterValues("Имя");
        if (filterValues != null) {
            if (specification == null) {
                specification = hasName(filterValues.get("lastName"), filterValues.get("firstName"),
                        filterValues.get("middleName"));
            } else {
                specification = where(specification)
                        .and(hasName(filterValues.get("lastName"), filterValues.get("firstName"),
                                filterValues.get("middleName")));
            }
        }
        filterValues = filtersMap.getFilterValues("Номер паспорта");
        if (filterValues != null) {
            String passportNumber = filterValues.get("passportNumber");
            if (passportNumber != null && !passportNumber.equals("")) {
                if (specification == null) {
                    specification = hasPassportNumber(Long.valueOf(passportNumber));
                } else {
                    specification = where(specification).and(hasPassportNumber(Long.valueOf(passportNumber)));
                }
            }
        }
        if (specification != null) {
            return personService.findAll(specification);
        } else {
            return personService.findAll();
        }
    }
}

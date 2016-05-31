package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.LegalEntity;
import ru.ssk.service.AddressService;
import ru.ssk.service.LegalEntityService;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.Specifications.where;
import static ru.ssk.spec.LegalEntitySpecs.*;

/**
 * Created by user on 23.05.2016.
 */
@RestController
@RequestMapping("/entities")
@Transactional
public class LegalEntityController extends BaseController {
    @Autowired
    private LegalEntityService legalEntityService;
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<LegalEntity> allEntities(){
        return legalEntityService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public LegalEntity getOne(@RequestParam(value = "id") long id){
        return legalEntityService.findById(id);
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public String delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            legalEntityService.deleteWithIds(Arrays.asList(idsToDelete));
            return new Gson().toJson("Записи успешно удалены.");
        } else {
            return new Gson().toJson("Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String updatePerson(//@RequestParam(value = "id") long id,
                               @RequestParam(value = "entity") String person) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        LegalEntity legalEntity = gson.fromJson(person, LegalEntity.class);
        legalEntityService.save(legalEntity);
        return new Gson().toJson("Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String add(@RequestParam(value = "entity") String entity) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        LegalEntity legalEntity = gson.fromJson(entity, LegalEntity.class);
        synchronizeAddressSession(legalEntity);
        legalEntityService.save(legalEntity);
        return new Gson().toJson("Данные о юр. лице успешно сохранены в базе.");
    }

    private void synchronizeAddressSession(LegalEntity legalEntity) {
        Long addressId = legalEntity.getAddress().getId();
        if (addressId != null) {
            legalEntity.setAddress(addressService.findById(addressId));
        }
    }

    @RequestMapping(value = "/filter/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public List<LegalEntity> filter(@RequestParam(value = "filters") String filters) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        FiltersMap filtersMap = gson.fromJson(filters, FiltersMap.class);
        Map<String, String> filterValues = filtersMap.getFilterValues("Пресональный счёт");
        Specification<LegalEntity> specification = null;
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
            if (specification == null) {
                specification = hasPhone(phone);
            } else {
                specification = where(specification).and(hasPhone(phone));
            }
        }
        filterValues = filtersMap.getFilterValues("E-mail");
        if (filterValues != null) {
            String email = filterValues.get("email");
            if (specification == null) {
                specification = hasEmail(email);
            } else {
                specification = where(specification).and(hasEmail(email));
            }
        }
        filterValues = filtersMap.getFilterValues("Название");
        if (filterValues != null) {
            String name = filterValues.get("name");
            if (specification == null) {
                specification = hasName(name);
            } else {
                specification = where(specification).and(hasName(name));
            }
        }
        filterValues = filtersMap.getFilterValues("ОГРН");
        if (filterValues != null) {
            String ogrn = filterValues.get("ogrn");
            if (specification == null) {
                specification = hasOgrn(ogrn);
            } else {
                specification = where(specification).and(hasOgrn(ogrn));
            }
        }
        filterValues = filtersMap.getFilterValues("ИНН");
        if (filterValues != null) {
            String inn = filterValues.get("inn");
            if (specification == null) {
                specification = hasInn(inn);
            } else {
                specification = where(specification).and(hasInn(inn));
            }
        }
        filterValues = filtersMap.getFilterValues("КПП");
        if (filterValues != null) {
            String kpp = filterValues.get("kpp");
            if (specification == null) {
                specification = hasKpp(kpp);
            } else {
                specification = where(specification).and(hasKpp(kpp));
            }
        }
        filterValues = filtersMap.getFilterValues("Дата регистрации");
        if (filterValues != null) {
            String dateFromString = filterValues.get("registrationDate");
            if (dateFromString != null) {
                Date date = Date.valueOf(dateFromString.substring(0, 10));
                if (specification == null) {
                    specification = registeredOn(date);
                } else {
                    specification = where(specification).and(registeredOn(date));
                }
            }
        }
        if (specification != null) {
            return legalEntityService.findAll(specification);
        } else {
            return legalEntityService.findAll();
        }
    }
}

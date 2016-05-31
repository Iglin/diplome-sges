package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.Address;
import ru.ssk.model.PersonStatement;
import ru.ssk.service.AddressService;
import ru.ssk.service.MeteringPointService;
import ru.ssk.service.PersonStatementService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 31.05.2016.
 */
@RestController
@RequestMapping("/person_statements")
@Transactional
public class PersonStatementController extends BaseController {
    @Autowired
    private PersonStatementService personStatementService;
    @Autowired
    private MeteringPointService meteringPointService;
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<PersonStatement> all(){
        return personStatementService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> openEditor(@RequestParam(value = "id", required = false) String id) {
        Map<String, Object> params = new HashMap<>(5);
        if (id != null) {
            params.put("statement", personStatementService.findById(Long.parseLong(id)));
        }
        params.put("points", meteringPointService.findAllPersonPoints());
        return params;
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public String delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            personStatementService.deleteWithIds(Arrays.asList(idsToDelete));
            return new Gson().toJson("Записи успешно удалены.");
        } else {
            return new Gson().toJson("Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String updatePerson(@RequestParam(value = "statement") String point) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        PersonStatement personStatement = gson.fromJson(point, PersonStatement.class);
        System.out.println(point);
        System.out.println(personStatement.getMeteringPoint());
        personStatementService.save(personStatement);
        return new Gson().toJson("Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String add(@RequestParam(value = "statement") String statement) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        PersonStatement personStatement = gson.fromJson(statement, PersonStatement.class);
        if (personStatement.getMeteringPoint().getId() == null) {
            Address address = personStatement.getMeteringPoint().getAddress();
            if (address.getId() != null) {
                personStatement.getMeteringPoint().setAddress(addressService.findById(address.getId()));
            }
            long id = meteringPointService.save(personStatement.getMeteringPoint()).getId();
            personStatement.setMeteringPoint(meteringPointService.findById(id));
        }
        personStatementService.save(personStatement);
        return new Gson().toJson("Данные о заявлении успешно сохранены в базе.");
    }
}

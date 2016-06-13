package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.Address;
import ru.ssk.model.EntityStatement;
import ru.ssk.model.MeteringPoint;
import ru.ssk.service.AddressService;
import ru.ssk.service.EntityStatementService;
import ru.ssk.service.MeteringPointService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 29.05.2016.
 */
@RestController
@RequestMapping("/entity_statements")
@Transactional
public class EntityStatementController extends BaseController {
    @Autowired
    private EntityStatementService entityStatementService;
    @Autowired
    private MeteringPointService meteringPointService;
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<EntityStatement> all(){
        return entityStatementService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> openEditor(@RequestParam(value = "id", required = false) String id) {
        Map<String, Object> params = new HashMap<>(5);
        if (id != null) {
            params.put("statement", entityStatementService.findById(Long.parseLong(id)));
        }
        params.put("points", meteringPointService.findAllEntityPoints());
        return params;
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            entityStatementService.deleteWithIds(Arrays.asList(idsToDelete));
            return new ResponseMessage(true, "Записи успешно удалены.");
        } else {
            return new ResponseMessage(false, "Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage updatePerson(@RequestParam(value = "statement") String point) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        EntityStatement entityStatement = gson.fromJson(point, EntityStatement.class);
        System.out.println(point);
        System.out.println(entityStatement.getMeteringPoint());
        entityStatementService.save(entityStatement);
        return new ResponseMessage(true, "Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage add(@RequestParam(value = "statement") String statement) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        EntityStatement entityStatement = gson.fromJson(statement, EntityStatement.class);
        if (entityStatement.getMeteringPoint().getId() == null) {
            Address address = entityStatement.getMeteringPoint().getAddress();
            if (address.getId() != null) {
                entityStatement.getMeteringPoint().setAddress(addressService.findById(address.getId()));
            }
            long id = meteringPointService.save(entityStatement.getMeteringPoint()).getId();
            entityStatement.setMeteringPoint(meteringPointService.findById(id));
        }
        entityStatementService.save(entityStatement);
        return new ResponseMessage(true, "Данные о заявлении успешно сохранены в базе.");
    }
}

package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.ExtraService;
import ru.ssk.service.ExtraServiceService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 01.06.2016.
 */
@RestController
@RequestMapping("/extra_services")
@Transactional
public class ExtraServiceController extends BaseController {
    @Autowired
    private ExtraServiceService service;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ExtraService> all(){
        return service.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ExtraService getOne(@RequestParam(value = "id") long id){
        return service.findById(id);
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage delete(@RequestParam(value = "ids") Long[] idsToDelete) {
        if (idsToDelete.length > 0) {
            service.deleteWithIds(Arrays.asList(idsToDelete));
            return new ResponseMessage(true, "Услуги удалены.");
        } else {
            return new ResponseMessage(false, "Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage update(@RequestParam(value = "service") String serviceJSON) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        ExtraService extraService = gson.fromJson(serviceJSON, ExtraService.class);
        service.save(extraService);
        return new ResponseMessage(true, "Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage add(@RequestParam(value = "service") String serviceJSON) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        ExtraService extraService = gson.fromJson(serviceJSON, ExtraService.class);
        service.save(extraService);
        return new ResponseMessage(true, "Данные об услуге успешно сохранены в базе.");
    }
}

package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.Meter;
import ru.ssk.service.MeterModelService;
import ru.ssk.service.MeterService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 25.05.2016.
 */
@RestController
@RequestMapping("/meters")
@Transactional
public class MeterController extends BaseController {
    @Autowired
    private MeterService meterService;
    @Autowired
    private MeterModelService meterModelService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Meter> allEntities(){
        return meterService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> openEditor(@RequestParam(value = "id", required = false) String id) {
        Map<String, Object> params = new HashMap<>(2);
        if (id != null) {
            params.put("meter", meterService.findById(Long.parseLong(id)));
        }
        params.put("models", meterModelService.findAll());
        return params;
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            meterService.deleteWithIds(Arrays.asList(idsToDelete));
            return new ResponseMessage(true, "Записи успешно удалены.");
        } else {
            return new ResponseMessage(false, "Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage updatePerson(@RequestParam(value = "meter") String meterJSON) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        Meter meter = gson.fromJson(meterJSON, Meter.class);
        meterService.save(meter);
        return new ResponseMessage(true, "Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage add(@RequestParam(value = "meter") String meterJSON) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        Meter meter = gson.fromJson(meterJSON, Meter.class);
        synchronizeAddressSession(meter);
        meterService.save(meter);
        return new ResponseMessage(true, "Данные о счётчике успешно сохранены в базе.");
    }

    private void synchronizeAddressSession(Meter meter) {
        Long modelId = meter.getModel().getId();
        if (modelId != null) {
            meter.setModel(meterModelService.findById(modelId));
        }
    }
}

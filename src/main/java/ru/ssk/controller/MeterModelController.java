package ru.ssk.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.MeterModel;
import ru.ssk.service.MeterModelService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
@RestController
@RequestMapping("/models")
@Transactional
public class MeterModelController extends BaseController {
    @Autowired
    private MeterModelService meterModelService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<MeterModel> allModels(){
        return meterModelService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MeterModel getOne(@RequestParam(value = "id") long id){
        return meterModelService.findById(id);
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            meterModelService.deleteWithIds(Arrays.asList(idsToDelete));
            return new ResponseMessage(true, "Выбранные модели успешно удалены.");
        } else {
            return new ResponseMessage(false, "Не выбраны модели для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage update(@RequestParam(value = "model") String model){
        MeterModel meterModel = new Gson().fromJson(model, MeterModel.class);
        meterModelService.save(meterModel);
        return new ResponseMessage(true, "Информацию о модели успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage add(@RequestParam(value = "model") String model){
        MeterModel meterModel = new Gson().fromJson(model, MeterModel.class);
        meterModelService.save(meterModel);
        return new ResponseMessage(true, "Модель счётчика успешно сохранёна в базе.");
    }

    @RequestMapping(value = "/info/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MeterModel getPassport(@RequestParam(value = "id") Long modelId) {
        return meterModelService.findById(modelId);
    }
}

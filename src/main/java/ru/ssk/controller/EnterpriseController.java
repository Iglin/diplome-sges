package ru.ssk.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.Enterprise;
import ru.ssk.service.AddressService;
import ru.ssk.service.EnterpriseService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
@RestController
@RequestMapping("/enterprise")
@Transactional
public class EnterpriseController extends BaseController {
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Enterprise> all(){
        return enterpriseService.findAll();
    }

    @RequestMapping(value = "/table/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public List<Enterprise> setActual(@RequestParam(value = "id") long id){
        enterpriseService.setActual(id);
        return enterpriseService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Enterprise getOne(@RequestParam(value = "id") long id){
        return enterpriseService.findById(id);
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            enterpriseService.deleteWithIds(Arrays.asList(idsToDelete));
            return new ResponseMessage(true, "Записи успешно удалены.");
        } else {
            return new ResponseMessage(false, "Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage updatePerson(@RequestParam(value = "enterprise") String person) {
        Enterprise enterprise = new Gson().fromJson(person, Enterprise.class);
        enterpriseService.save(enterprise);
        return new ResponseMessage(true, "Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage add(@RequestParam(value = "enterprise") String entity) {
        Enterprise enterprise = new Gson().fromJson(entity, Enterprise.class);
        synchronizeAddressSession(enterprise);
        enterpriseService.save(enterprise);
        return new ResponseMessage(true, "Данные о предприятии успешно сохранены в базе.");
    }

    @RequestMapping(value = "/info/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Enterprise getPassport(@RequestParam(value = "id") Long id) {
        return enterpriseService.findById(id);
    }

    private void synchronizeAddressSession(Enterprise enterprise) {
        Long addressId = enterprise.getBankAddress().getId();
        if (addressId != null) {
            enterprise.setBankAddress(addressService.findById(addressId));
        }
    }
}

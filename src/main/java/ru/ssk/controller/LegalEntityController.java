package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.LegalEntity;
import ru.ssk.service.AddressService;
import ru.ssk.service.LegalEntityService;

import java.util.Arrays;
import java.util.List;

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
     //   legalEntity.setId(id);
        //  synchronizeAddressesSession(physicalPerson);
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
}

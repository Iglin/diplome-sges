package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.ActOfProvidingServices;
import ru.ssk.service.ActService;
import ru.ssk.service.AgreementService;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 10.06.2016.
 */
@RestController
@RequestMapping("/acts")
@Transactional
public class ActController {
    @Autowired
    private ActService actService;
    @Autowired
    private AgreementService agreementService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ActOfProvidingServices> allEntities(){
        return actService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> openEditor(@RequestParam(value = "id", required = false) Long id) {
        Map<String, Object> params = new HashMap<>(2);
        if (id != null) {
            params.put("act", actService.findByNumber(id));
        }
        params.put("agreements", agreementService.findAll());
        return params;
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            actService.deleteWithIds(Arrays.asList(idsToDelete));
            return new ResponseMessage(true, "Записи успешно удалены.");
        } else {
            return new ResponseMessage(false, "Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage update(//@RequestParam(value = "id") long id,
                                        @RequestParam(value = "act") String json) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        ActOfProvidingServices act = gson.fromJson(json, ActOfProvidingServices.class);
        actService.save(act);
        return new ResponseMessage(true, "Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage add(@RequestParam(value = "act") String json) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        ActOfProvidingServices act = gson.fromJson(json, ActOfProvidingServices.class);
     //   synchronizeAddressSession(act);
        actService.save(act);
        return new ResponseMessage(true, "Акт об оказании услуг успешно сохранён в базе.");
    }

   /* private void synchronizeAgreementsSession(LegalEntity legalEntity) {
        Long addressId = legalEntity.getAddress().getId();
        if (addressId != null) {
            legalEntity.setAddress(addressService.findById(addressId));
        }
    }*/
}

package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.Agreement;
import ru.ssk.model.LegalEntity;
import ru.ssk.model.ServiceInAgreement;
import ru.ssk.service.AddressService;
import ru.ssk.service.AgreementService;
import ru.ssk.service.ExtraServiceService;
import ru.ssk.service.MeteringPointService;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by user on 01.06.2016.
 */
@RestController
@RequestMapping("/agreements")
@Transactional
public class AgreementController extends BaseController {
    @Autowired
    private AgreementService agreementService;
    @Autowired
    private MeteringPointService meteringPointService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ExtraServiceService extraServiceService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Agreement> all(){
        return agreementService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> openEditor(@RequestParam(value = "id", required = false) String id) {
        Map<String, Object> params = new HashMap<>(2);
        if (id != null) {
            Agreement agreement = agreementService.findByNumber(Long.parseLong(id));
            agreement.getServices().forEach(ServiceInAgreement::getExtraService);
            params.put("agreement", agreement);
            if (agreement.getMeteringPoint().getOwner() instanceof LegalEntity) {
                params.put("points", meteringPointService.findAllEntityPoints());
            } else {
                params.put("points", meteringPointService.findAllPersonPoints());
            }
        } else {
            params.put("points", meteringPointService.findAllEntityPoints());
        }
        params.put("services", extraServiceService.findAll());
        return params;
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public String delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            agreementService.deleteWithIds(Arrays.asList(idsToDelete));
            return new Gson().toJson("Записи успешно удалены.");
        } else {
            return new Gson().toJson("Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestParam(value = "agreement") String agreementJSON,
                         @RequestParam(value = "services") String servicesToSend) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        Agreement agreement = gson.fromJson(agreementJSON, Agreement.class);
        Type projectListType = new TypeToken<List<ServiceInAgreement>>() {}.getType();
        List<ServiceInAgreement> services = gson.fromJson("[" + servicesToSend + "]", projectListType);
        Set<ServiceInAgreement> set = new HashSet<>();
        for (ServiceInAgreement serviceInAgreement : services) {
            serviceInAgreement.setAgreement(agreement);
            set.add(serviceInAgreement);
        }
        agreement.setServices(set);
        agreement.calculateTotal();

        agreementService.save(agreement, true);
        return new Gson().toJson("Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String add(@RequestParam(value = "agreement") String agreementJSON,
                      @RequestParam(value = "services") String servicesToSend) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        Agreement agreement = gson.fromJson(agreementJSON, Agreement.class);
        Type projectListType = new TypeToken<List<ServiceInAgreement>>() {}.getType();
        List<ServiceInAgreement> services = gson.fromJson("[" + servicesToSend + "]", projectListType);
        Set<ServiceInAgreement> set = new HashSet<>();
        for (ServiceInAgreement serviceInAgreement : services) {
            serviceInAgreement.setAgreement(agreement);
            set.add(serviceInAgreement);
        }
        if (agreement.getMeteringPoint().getId() == null) {
            Long id = agreement.getMeteringPoint().getAddress().getId();
            if (id != null) {
                agreement.getMeteringPoint().setAddress(addressService.findById(id));
            }
            id = meteringPointService.save(agreement.getMeteringPoint()).getId();
            agreement.setMeteringPoint(meteringPointService.findById(id));
        }
        agreement.setServices(set);
        agreement.calculateTotal();

        agreementService.save(agreement, false);
        return new Gson().toJson("Данные о договоре успешно сохранены в базе.");
    }
}

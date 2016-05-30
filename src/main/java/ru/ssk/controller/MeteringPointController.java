package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.LegalEntity;
import ru.ssk.model.MeteringPoint;
import ru.ssk.service.*;
import ru.ssk.spec.MeteringPointSpecs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 25.05.2016.
 */
@RestController
@RequestMapping("/points")
@Transactional
public class MeteringPointController extends BaseController {
    @Autowired
    private MeteringPointService meteringPointService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private MeterService meterService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private LegalEntityService legalEntityService;
    @Autowired
    private PhysicalPersonService physicalPersonService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<MeteringPoint> all(){
        return meteringPointService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> openEditor(@RequestParam(value = "id", required = false) String id) {
        Map<String, Object> params = new HashMap<>(5);
        if (id != null) {
            params.put("point", meteringPointService.findById(Long.parseLong(id)));
        }
        params.put("addresses", addressService.findAll());
        params.put("meters", meterService.findAll());
        params.put("enterpriseEntries", enterpriseService.findAll());
        params.put("entities", legalEntityService.findAll());
        params.put("persons", physicalPersonService.findAll());
        return params;
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public String delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            meteringPointService.deleteWithIds(Arrays.asList(idsToDelete));
            return new Gson().toJson("Записи успешно удалены.");
        } else {
            return new Gson().toJson("Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String updatePerson(@RequestParam(value = "point") String point) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        MeteringPoint meteringPoint = gson.fromJson(point, MeteringPoint.class);
        meteringPointService.save(meteringPoint);
        return new Gson().toJson("Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String add(@RequestParam(value = "point") String point) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        MeteringPoint meteringPoint = gson.fromJson(point, MeteringPoint.class);
        synchronizeAddressSession(meteringPoint);
        meteringPointService.save(meteringPoint);
        return new Gson().toJson("Данные о точке учёта успешно сохранены в базе.");
    }

    @RequestMapping(value = "/info/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MeteringPoint getInfo(@RequestParam(value = "id") Long id) {
        MeteringPoint meteringPoint = meteringPointService.findById(id);
        if (meteringPoint.getOwner() instanceof LegalEntity) {
            meteringPoint.setOwner(legalEntityService.findById(meteringPoint.getOwner().getId()));
        } else {
            meteringPoint.setOwner(physicalPersonService.findById(meteringPoint.getOwner().getId()));
        }
        return meteringPoint;
    }

    @RequestMapping(value = "/filter/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public List<MeteringPoint> filter(@RequestParam(value = "filters") String filters) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        FiltersMap filtersMap = gson.fromJson(filters, FiltersMap.class);
        String entityName = filtersMap.getFiltersValue("Собственник");
        System.out.println(entityName);
        return meteringPointService.findAll(MeteringPointSpecs.ownedByEntity(entityName));
    }

    private void synchronizeAddressSession(MeteringPoint meteringPoint) {
        Long addressId = meteringPoint.getAddress().getId();
        if (addressId != null) {
            meteringPoint.setAddress(addressService.findById(addressId));
        }
    }
}

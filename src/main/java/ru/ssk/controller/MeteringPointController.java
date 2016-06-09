package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.LegalEntity;
import ru.ssk.model.MeteringPoint;
import ru.ssk.model.PhysicalPerson;
import ru.ssk.service.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.jpa.domain.Specifications.where;
import static ru.ssk.spec.MeteringPointSpecs.*;

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
    public ResponseMessage delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            meteringPointService.deleteWithIds(Arrays.asList(idsToDelete));
            return new ResponseMessage(true, "Записи успешно удалены.");
        } else {
            return new ResponseMessage(false, "Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage updatePerson(@RequestParam(value = "point") String point) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        MeteringPoint meteringPoint = gson.fromJson(point, MeteringPoint.class);
        meteringPointService.save(meteringPoint);
        return new ResponseMessage(true, "Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage add(@RequestParam(value = "point") String point) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        MeteringPoint meteringPoint = gson.fromJson(point, MeteringPoint.class);
        synchronizeAddressSession(meteringPoint);
        meteringPointService.save(meteringPoint);
        return new ResponseMessage(true, "Данные о точке учёта успешно сохранены в базе.");
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
    public List<MeteringPoint> filterEntityPoints(@RequestParam(value = "filters") String filters,
                                                  @RequestParam(value = "ownerType") String ownerType) {
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        FiltersMap filtersMap = gson.fromJson(filters, FiltersMap.class);
        Map<String, String> filterValues = filtersMap.getFilterValues("Собственник");
        Specification<MeteringPoint> specification = null;
        if (filterValues != null) {
            specification = ownedBy(Long.valueOf(filterValues.get("personalAccount")));
        }
        filterValues = filtersMap.getFilterValues("Адрес");
        if (filterValues != null) {
            String region = filterValues.get("region");
            String street = filterValues.get("street");
            String city = filterValues.get("city");
            String building = filterValues.get("building");
            String apartment = filterValues.get("apartment");
            String index = filterValues.get("index");
            if (specification == null) {
                specification = locatedIn(region, city, street, building, apartment, index);
            } else {
                specification = where(specification).and(locatedIn(region, city, street, building, apartment, index));
            }
        }
        filterValues = filtersMap.getFilterValues("Счётчик");
        if (filterValues != null) {
            String manufacturer = filterValues.get("manufacturer");
            String model = filterValues.get("model");
            String serialNumber = filterValues.get("serialNumber");
            if (specification == null) {
                specification = hasMeter(manufacturer, model, serialNumber);
            } else {
                specification = where(specification).and(hasMeter(manufacturer, model, serialNumber));
            }
        }
        filterValues = filtersMap.getFilterValues("Дата установки");
        if (filterValues != null) {
            String dateFromString = filterValues.get("dateFrom");
            String dateToString = filterValues.get("dateTo");
            if (dateFromString != null && dateToString != null) {
                Date dateFrom = Date.valueOf(dateFromString.substring(0, 10));
                Date dateTo = Date.valueOf(dateToString.substring(0, 10));
                if (specification == null) {
                    specification = installedInPeriod(dateFrom, dateTo);
                } else {
                    specification = where(specification).and(installedInPeriod(dateFrom, dateTo));
                }
            }
        }
        if (ownerType.equals("entity")) {
            if (specification != null) {
                List<MeteringPoint> list = meteringPointService.findAll(specification);
                List<MeteringPoint> result = new ArrayList<>(list.size());
                list.forEach(meteringPoint -> {
                    if (meteringPoint.getOwner() instanceof LegalEntity) result.add(meteringPoint);
                });
                return result;
            } else {
                return meteringPointService.findAllEntityPoints();
            }
        } else {
            if (specification != null) {
                List<MeteringPoint> list = meteringPointService.findAll(specification);
                List<MeteringPoint> result = new ArrayList<>(list.size());
                list.forEach(meteringPoint -> {
                    if (meteringPoint.getOwner() instanceof PhysicalPerson) result.add(meteringPoint);
                });
                return result;
            } else {
                return meteringPointService.findAllPersonPoints();
            }
        }
    }

    private void synchronizeAddressSession(MeteringPoint meteringPoint) {
        Long addressId = meteringPoint.getAddress().getId();
        if (addressId != null) {
            meteringPoint.setAddress(addressService.findById(addressId));
        }
    }
}

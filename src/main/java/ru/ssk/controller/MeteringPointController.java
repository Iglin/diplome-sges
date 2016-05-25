package ru.ssk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.MeteringPoint;
import ru.ssk.service.AddressService;
import ru.ssk.service.MeteringPointService;

import java.util.Arrays;
import java.util.List;

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

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<MeteringPoint> all(){
        return meteringPointService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MeteringPoint getOne(@RequestParam(value = "id") long id){
        return meteringPointService.findById(id);
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

  /*  @RequestMapping(value = "/info/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MeteringPoint getInfo(@RequestParam(value = "id") Long id) {
        return meteringPointService.findById(id);
    }
*/
    private void synchronizeAddressSession(MeteringPoint meteringPoint) {
        Long addressId = meteringPoint.getAddress().getId();
        if (addressId != null) {
            meteringPoint.setAddress(addressService.findById(addressId));
        }
    }
}

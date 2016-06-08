package ru.ssk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.Address;
import ru.ssk.service.AddressService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 15.05.2016.
 */
@RestController
@RequestMapping("/addresses")
@Transactional
public class AddressController extends BaseController {
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Address> allAddresses(){
        return addressService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Address getOne(@RequestParam(value = "id") long id){
        return addressService.findById(id);
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            addressService.deleteAddressesWithIds(Arrays.asList(idsToDelete));
            return new ResponseMessage(true, "Адреса успешно удалены.");
        } else {
            return new ResponseMessage(false, "Не выбраны адреса для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage update(@RequestParam(value = "id") long id,
                         @RequestParam(value = "region") String region,
                         @RequestParam(value = "city") String city,
                         @RequestParam(value = "street") String street,
                         @RequestParam(value = "building") String building,
                         @RequestParam(value = "apartment", required = false) String apartment,
                         @RequestParam(value = "index") int index){
        Address address = addressService.findById(id);
        address.setRegion(region);
        address.setCity(city);
        address.setStreet(street);
        address.setBuilding(building);
        address.setApartment(apartment);
        address.setIndex(index);
        addressService.save(address);
        return new ResponseMessage(true, "Адрес успешно обновлён.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage add(@RequestParam(value = "region") String region,
                      @RequestParam(value = "city") String city,
                      @RequestParam(value = "street") String street,
                      @RequestParam(value = "building") String building,
                      @RequestParam(value = "apartment", required = false) String apartment,
                      @RequestParam(value = "index") int index){
        Address address = new Address();
        address.setRegion(region);
        address.setCity(city);
        address.setStreet(street);
        address.setBuilding(building);
        address.setApartment(apartment);
        address.setIndex(index);
        addressService.save(address);
        return new ResponseMessage(true, "Адрес успешно сохранён в базе.");
    }
}

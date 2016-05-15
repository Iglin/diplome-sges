package ru.ssk.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.Address;
import ru.ssk.service.AddressService;

import java.util.List;

/**
 * Created by user on 15.05.2016.
 */
@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseController {
    @Autowired
    AddressService addressService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Address> all(){
        return addressService.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String add(@RequestParam(value = "region") String region,
                      @RequestParam(value = "city") String city,
                      @RequestParam(value = "street") String street,
                      @RequestParam(value = "building") String building,
                      @RequestParam(value = "apartment") String apartment,
                      @RequestParam(value = "index") int index){
        Address address = new Address();
        address.setRegion(region);
        address.setCity(city);
        address.setStreet(street);
        address.setBuilding(building);
        address.setApartment(apartment);
        address.setIndex(index);
        addressService.add(address);
        return new Gson().toJson("Адрес успешно сохранён в базе.");
    }
}

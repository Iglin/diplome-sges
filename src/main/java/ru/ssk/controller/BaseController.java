package ru.ssk.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ssk.exception.DatabaseConnectionException;
import ru.ssk.exception.DuplicateDataException;
import ru.ssk.exception.MultipleRepresentationsException;
import ru.ssk.exception.UniqueViolationException;
import ru.ssk.model.Address;
import ru.ssk.service.AddressService;
import ru.ssk.service.MeteringPointService;

/**
 * Created by user on 13.05.2016.
 */
@Controller
public abstract class BaseController {

    @ExceptionHandler(value = {
            UniqueViolationException.class,
            DuplicateDataException.class,
            MultipleRepresentationsException.class,
            DatabaseConnectionException.class })
    public String handle(Exception e){
        return new Gson().toJson(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception e) {
        e.printStackTrace();
        return new Gson().toJson("Произошла непредвиденная ошибка.");
    }
}

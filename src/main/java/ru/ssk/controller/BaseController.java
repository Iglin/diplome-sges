package ru.ssk.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ssk.exception.DataSynchronizationException;
import ru.ssk.exception.DuplicateDataException;
import ru.ssk.exception.UniqueViolationException;

/**
 * Created by user on 13.05.2016.
 */
@Controller
public abstract class BaseController {
    @ExceptionHandler(value = { UniqueViolationException.class, DuplicateDataException.class, DataSynchronizationException.class})
    public String handle(Exception e){
        return new Gson().toJson(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception e) {
        e.printStackTrace();
        return new Gson().toJson("Произошла непредвиденная ошибка.");
    }
}

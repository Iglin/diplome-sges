package ru.ssk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ssk.exception.UniqueViolationException;

/**
 * Created by user on 13.05.2016.
 */
@Controller
public abstract class BaseController {
    @ExceptionHandler(value = { UniqueViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(Exception e){
        return e.getMessage();
    }
}

package ru.ssk.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 13.05.2016.
 */
@RestController
@RequestMapping("/main")
public class MainController extends BaseController {
    @RequestMapping(value = "/current_user", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getCurrentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new Gson().toJson(authentication);
    }
}

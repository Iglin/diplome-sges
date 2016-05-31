package ru.ssk.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.User;
import ru.ssk.service.UserService;

/**
 * Created by user on 01.06.2016.
 */
@RestController
@RequestMapping("/user")
@Transactional
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByLogin(authentication.getName());
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestParam(value = "user") String userJson){
        User user = new Gson().fromJson(userJson, User.class);
        return userService.save(user);
    }
}

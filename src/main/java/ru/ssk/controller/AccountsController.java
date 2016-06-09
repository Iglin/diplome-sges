package ru.ssk.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.model.User;
import ru.ssk.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 02.06.2016.
 */
@RestController
@RequestMapping("/accounts")
@Transactional
public class AccountsController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/table/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> all(){
        return userService.findAll();
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getOne(@RequestParam(value = "id") long id){
        return userService.findById(id);
    }

    @RequestMapping(value = "/table/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage delete(@RequestParam(value = "ids") Long[] idsToDelete) {

        if (idsToDelete.length > 0) {
            userService.deleteWithIds(Arrays.asList(idsToDelete));
            return new ResponseMessage(true, "Записи успешно удалены.");
        } else {
            return new ResponseMessage(false, "Не выбраны записи для удаления.");
        }
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage update(@RequestParam(value = "account") String account) {
        User user = new Gson().fromJson(account, User.class);
        userService.save(user);
        return new ResponseMessage(true, "Запись успешно обновлена.");
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage add(@RequestParam(value = "account") String account) {
        User user = new Gson().fromJson(account, User.class);
        userService.save(user);
        return new ResponseMessage(true, "Учётная запись зарегистрирована.");
    }
}

package ru.ssk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.ssk.model.Owner;
import ru.ssk.service.OwnerService;

import java.util.List;

/**
 * Created by user on 29.03.2016.
 */
@Controller
public class HomeController {

    @Autowired
    private OwnerService ownerService;

    @RequestMapping({"/", "/index", "/home"})
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Owner owner = new Owner("+7903333333", "test@mail.ru", 133);
        ownerService.add(owner);
        List<Owner> list = ownerService.listAll();
        modelAndView.addObject("owners", list);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}

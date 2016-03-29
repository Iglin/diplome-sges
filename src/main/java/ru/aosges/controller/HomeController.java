package ru.aosges.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.aosges.service.OwnerService;

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
        ownerService.listAll();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}

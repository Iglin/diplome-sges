package ru.aosges.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.aosges.service.OwnerService;

/**
 * Created by root on 14.03.16.
 */
@Controller
public class HomeController {

    @Autowired
    OwnerService ownerService;

    @RequestMapping({"/", "/welcome"})
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("welcome");
        return modelAndView;
    }
}

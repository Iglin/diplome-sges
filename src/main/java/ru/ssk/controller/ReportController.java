package ru.ssk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ssk.reporting.ReportBuilder;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by user on 10.06.2016.
 */
@RestController
@RequestMapping("/reports")
@Transactional
public class ReportController extends BaseController {
    @Autowired
    private ReportBuilder reportBuilder;

    @RequestMapping(value = "/make/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void agreements(@RequestParam(value = "dateFrom") String dateFrom,
                           @RequestParam(value = "dateTo") String dateTo,
                           @RequestParam(value = "isEntity") boolean isEntity) throws ParseException {
       // Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        Date date1 = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom).getTime());
        Date date2 = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateTo).getTime());
        reportBuilder.generateAgreementsRegistry(date1, date2, isEntity);
    }

    @RequestMapping(value = "/make/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void acts(@RequestParam(value = "dateFrom") String dateFrom,
                           @RequestParam(value = "dateTo") String dateTo,
                           @RequestParam(value = "isEntity") boolean isEntity) throws ParseException {
       // Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        Date date1 = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom).getTime());
        Date date2 = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateTo).getTime());
        reportBuilder.generateActsRegistry(date1, date2, isEntity);
    }

    @RequestMapping(value = "/make/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void commercial(@RequestParam(value = "dateFrom") String dateFrom,
                     @RequestParam(value = "dateTo") String dateTo) throws ParseException {
        // Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        Date date1 = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom).getTime());
        Date date2 = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateTo).getTime());
        reportBuilder.generateCommercialReport(date1, date2);
    }
}

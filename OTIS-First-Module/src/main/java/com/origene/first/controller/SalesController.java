package com.origene.first.controller;

import com.origene.first.service.SaleService;
import com.origene.model.dto.Parameters;
import com.origene.model.dto.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("Sales")
public class SalesController {

    @Autowired
    private SaleService saleService;

    @RequestMapping("SellOut")
    public List<Statistics> SellOut() throws Exception {

        return saleService.SellOut();
    }

    @RequestMapping("Regional_sales")
    public List<Statistics> RegionalSales(){

        return saleService.RegionalSales();
//        return saleService.RegionalSales();
    }

    @RequestMapping("PaymentInReturn")
    public List<Statistics> PaymentInReturn(){

        return saleService.PaymentInReturn();
    }

    @RequestMapping("Annual")
    public List<Statistics> Annual(@RequestBody Parameters parameters){
        return saleService.Annual(parameters);
    }


}

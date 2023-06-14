package com.origene.first.service.impl;

import com.origene.first.mapper.SaleMapper;
import com.origene.first.service.SaleService;
import com.origene.model.dto.Parameters;
import com.origene.model.dto.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleMapper saleMapper;

    @Override
    public List<Statistics> SellOut() throws Exception {
        //        Calendar instance = Calendar.getInstance();
//        instance.add(Calendar.DATE,-30);
//        Date strtime = instance.getTime();
//        System.out.println(instance.getTime());
//        Date outtime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date strtime = format.parse("2020-11-10");
        Date outtime = format.parse("2020-11-17");
        return saleMapper.SellOut(strtime,outtime);
    }

    @Override
    public List<Statistics> RegionalSales() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,-1);
        Date time = calendar.getTime();
        calendar.add(Calendar.YEAR,-1);
        Date time1 = calendar.getTime();
        List<Statistics> statistics = saleMapper.RegionalSales(date, time, time1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if (statistics.size()>0){
            Statistics statis = statistics.get(0);
            HashMap<String, Object> maps = new HashMap<>();
            try{
                maps.put("date",format.format(date));
                maps.put("time",format.format(time));
                maps.put("time1", format.format(time1));
            }catch (Exception e){
                e.printStackTrace();
            }

            statis.setMaps(maps);
        }
        return statistics;
    }

    @Override
    public List<Statistics> PaymentInReturn() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = "2022-06-6";
        String enddate = "2022-08-13";
        Date str = null;
        Date end = null;
        try{
            str = format.parse(strdate);
            end = format.parse(enddate);
        }catch (Exception e){
            e.printStackTrace();
        }

        return saleMapper.PaymentInReturn(str,end);
    }

    @Override
    public List<Statistics> Annual(Parameters parameters) {

        String sta = parameters.getYeara()+"-01-01";
        String sta1 = parameters.getYeara()-1+"-01-01";


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,-1);
        Date end1 = calendar.getTime();
        Parameters param = new Parameters();
        param.setStaDate(sta);
        param.setStaDateo(sta1);

        try {
            param.setEndDate(format.format(date));
            param.setEndDateo(format.format(end1));

        }catch (Exception e){

        }




        System.out.println(param.getStaDate()+"\n"+param.getEndDate()+"\n"+param.getStaDateo()+"\n"+param.getEndDateo());
        Integer annual = saleMapper.Annual(param);
        Integer annualo = saleMapper.Annualo(param);
        List<Statistics> stat = new ArrayList<Statistics>();
        Statistics statistics = new Statistics();
        statistics.setSum((double)annual);
        statistics.setSum((double)annualo);
        return stat;
    }
}

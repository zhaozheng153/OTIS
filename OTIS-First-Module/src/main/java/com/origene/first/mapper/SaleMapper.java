package com.origene.first.mapper;

import com.origene.model.dto.Parameters;
import com.origene.model.dto.Statistics;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SaleMapper {
    List<Statistics> SellOut(@Param("strtime") Date strtime,@Param("outtime") Date outtime);

    List<Statistics> RegionalSales(@Param("date") Date date,@Param("time") Date time,@Param("time1") Date time1);

    List<Statistics> PaymentInReturn(@Param("str") Date str,@Param("end") Date end);

    Double Annual(Parameters param);
    Double Annualo(Parameters param);

}

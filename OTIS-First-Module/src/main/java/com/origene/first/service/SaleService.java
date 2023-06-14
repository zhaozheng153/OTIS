package com.origene.first.service;

import com.origene.model.dto.Parameters;
import com.origene.model.dto.Statistics;

import java.util.Date;
import java.util.List;

public interface SaleService {
    List<Statistics> SellOut() throws Exception;

    List<Statistics> RegionalSales();

    List<Statistics> PaymentInReturn();

    List<Statistics> Annual(Parameters parameters);
}

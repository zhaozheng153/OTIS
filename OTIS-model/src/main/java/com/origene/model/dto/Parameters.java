package com.origene.model.dto;

import java.io.Serializable;
import java.util.Date;

public class Parameters implements Serializable {

    private Date ymds;
    private Integer yeara;
    private String staDate;
    private String staDateo;
    private String endDate;
    private String endDateo;

    public Parameters() {
    }

    public Parameters(Date ymds, Integer yeara, String staDate, String staDateo, String endDate, String endDateo) {
        this.ymds = ymds;
        this.yeara = yeara;
        this.staDate = staDate;
        this.staDateo = staDateo;
        this.endDate = endDate;
        this.endDateo = endDateo;
    }


    public Integer getYeara() {
        return yeara;
    }

    public void setYeara(Integer yeara) {
        this.yeara = yeara;
    }

    public Date getYmds() {
        return ymds;
    }

    public void setYmds(Date ymds) {
        this.ymds = ymds;
    }

    public String getStaDate() {
        return staDate;
    }

    public void setStaDate(String staDate) {
        this.staDate = staDate;
    }

    public String getStaDateo() {
        return staDateo;
    }

    public void setStaDateo(String staDateo) {
        this.staDateo = staDateo;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDateo() {
        return endDateo;
    }

    public void setEndDateo(String endDateo) {
        this.endDateo = endDateo;
    }
}

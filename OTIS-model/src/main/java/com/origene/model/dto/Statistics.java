package com.origene.model.dto;

import java.io.Serializable;
import java.util.HashMap;

public class Statistics implements Serializable {

    private String name ;
    private Integer numone;
    private Double sum;
    private Double summ;
    private HashMap<String,Object> maps;

    public Statistics() {
    }

    public Statistics(String name, Integer numone, Double sum, Double summ, HashMap<String, Object> maps) {
        this.name = name;
        this.numone = numone;
        this.sum = sum;
        this.summ = summ;
        this.maps = maps;
    }

    public Double getSumm() {
        return summ;
    }

    public void setSumm(Double summ) {
        this.summ = summ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumone() {
        return numone;
    }

    public void setNumone(Integer numone) {
        this.numone = numone;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public HashMap<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(HashMap<String, Object> maps) {
        this.maps = maps;
    }




}

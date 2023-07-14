package com.origene.model.dto;

import java.io.Serializable;
import java.util.HashMap;

public class Statistics implements Serializable {

    private String name ;
    private Integer numone;
    private Double sum;
    private Double summ;
    private Integer Salesum;
    private Integer retsum;
    private Integer compsum;
    private Integer retgsum;
    private String message;
    private HashMap<String,Object> maps;

    public Statistics() {
    }

    public Statistics(String message) {
        this.message = message;
    }

    public Statistics(String name, Integer numone, Double sum, Double summ, Integer salesum, Integer retsum, Integer compsum, Integer retgsum, HashMap<String, Object> maps) {
        this.name = name;
        this.numone = numone;
        this.sum = sum;
        this.summ = summ;
        Salesum = salesum;
        this.retsum = retsum;
        this.compsum = compsum;
        this.retgsum = retgsum;
        this.maps = maps;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSalesum() {
        return Salesum;
    }

    public void setSalesum(Integer salesum) {
        Salesum = salesum;
    }

    public Integer getRetsum() {
        return retsum;
    }

    public void setRetsum(Integer retsum) {
        this.retsum = retsum;
    }

    public Integer getCompsum() {
        return compsum;
    }

    public void setCompsum(Integer compsum) {
        this.compsum = compsum;
    }

    public Integer getRetgsum() {
        return retgsum;
    }

    public void setRetgsum(Integer retgsum) {
        this.retgsum = retgsum;
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

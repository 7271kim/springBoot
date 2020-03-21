package com.seokjin.spring.springBoot.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class KospiModel {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@Temporal(TemporalType.DATE)
    @Column( nullable = false, unique=true )
    private String date;
    
    @Column( nullable = false )
    private Double todayPrice;
    
    @Column( nullable = false )
    private Double upDownSize;
    
    @Column( nullable = false )
    private Double percentage;
    
    @Column( nullable = false )
    private Double volumn;
    
    public KospiModel() {}

    public KospiModel(String date, Double todayPrice, Double upDownSize, Double percentage, Double volumn) {
        this.date = date;
        this.todayPrice = todayPrice;
        this.upDownSize = upDownSize;
        this.percentage = percentage;
        this.volumn = volumn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(Double todayPrice) {
        this.todayPrice = todayPrice;
    }

    public Double getUpDownSize() {
        return upDownSize;
    }

    public void setUpDownSize(Double upDownSize) {
        this.upDownSize = upDownSize;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getVolumn() {
        return volumn;
    }

    public void setVolumn(Double volumn) {
        this.volumn = volumn;
    }
    
}

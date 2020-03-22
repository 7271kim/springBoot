package com.seokjin.spring.springBoot.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Company implements Serializable {
    
    private static final long serialVersionUID = 3633041651419086069L;

    @Id
    @Column( nullable = false, length = 30 )
    private String date;
    
    @Column( nullable = false)
    private Double todayPrice;
    
    @Column( nullable = false )
    private Double upDownSize;
    
    @Column( nullable = false )
    private Double percentage;
    
    @Column( nullable = false )
    private Double volumn;
    
    @Column( nullable = false )
    private Double companyNetSales;
    
    @Column( nullable = false )
    private Double forignNetSales;
    
    @Column( nullable = false )
    private Double individualNetSales;

    @Column( nullable = false )
    private Double forignretentionRate;

    @Id
    @ManyToOne
    private CompanyDefault companyDefault;

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

    public Double getCompanyNetSales() {
        return companyNetSales;
    }

    public void setCompanyNetSales(Double companyNetSales) {
        this.companyNetSales = companyNetSales;
    }

    public Double getForignNetSales() {
        return forignNetSales;
    }

    public void setForignNetSales(Double forignNetSales) {
        this.forignNetSales = forignNetSales;
    }

    public Double getIndividualNetSales() {
        return individualNetSales;
    }

    public void setIndividualNetSales(Double individualNetSales) {
        this.individualNetSales = individualNetSales;
    }

    public Double getForignretentionRate() {
        return forignretentionRate;
    }

    public void setForignretentionRate(Double forignretentionRate) {
        this.forignretentionRate = forignretentionRate;
    }

    public CompanyDefault getCompanyDefault() {
        return companyDefault;
    }

    public void setCompanyDefault(CompanyDefault companyDefault) {
        this.companyDefault = companyDefault;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
}

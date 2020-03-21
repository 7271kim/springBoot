package com.seokjin.spring.springBoot.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Company {
    
    @Id
    @Column( nullable = false, unique=true )
    private String date;
    
    @ManyToOne
    private CompanyDefault companyDefault;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CompanyDefault getCompanyDefault() {
        return companyDefault;
    }

    public void setCompanyDefault(CompanyDefault companyDefault) {
        this.companyDefault = companyDefault;
    }
    
    
}

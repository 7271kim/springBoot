package com.seokjin.spring.springBoot.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CompanyDefault {
    @Id
    @Column( nullable = false, unique=true )
    private String companyCode;
    
    @Column( nullable = false, unique=true )
    private Integer rank;
    
    @Column( nullable = false, unique=true )
    private String companyName;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
    
    
    
}

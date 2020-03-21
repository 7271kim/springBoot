package com.seokjin.spring.springBoot.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.seokjin.spring.springBoot.service.GetCompanyData;
import com.seokjin.spring.springBoot.service.GetKospiData;

@Component
public class JpaRunner implements ApplicationRunner  {
    
    @Autowired
    GetKospiData getKospiDa;
    
    @Autowired
    GetCompanyData getCompanyData;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //getKospiDa.getKospiTotalDataIntoDB();
        //getKospiDa.getKospiTodayDataIntoDB(1314);
        getCompanyData.getKospi200Code();
    }

}

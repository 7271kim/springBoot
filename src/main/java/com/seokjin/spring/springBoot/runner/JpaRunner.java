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
        //getKospiDa.getKospiTodayDataIntoDB(1314);
        //getCompanyData.getKospi200Code();
        //getCompanyData.getCompanyAll(200);
        
        
        //매일매일 업데이트 부분
        //getKospiDa.getKospiTodayDataIntoDB(1);
        getCompanyData.getCompanyAll(1);
       
    }

}

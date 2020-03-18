package com.seokjin.spring.springBoot.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.seokjin.spring.springBoot.jpa.repository.Kospi200Repository;
import com.seokjin.spring.springBoot.service.GetKospi200;

@Component
public class JpaRunner implements ApplicationRunner  {
    
    @Autowired
    GetKospi200 getKospi200;
    
    @Autowired
    Kospi200Repository kospiDB;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //getKospi200.putKospiTotalDataIntoDB();
        getKospi200.putKospiDataIntoDB(1);
    }

}

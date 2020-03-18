package com.seokjin.spring.springBoot.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.seokjin.kim.library.HttpClientCustom;
import com.seokjin.spring.springBoot.jpa.repository.Kospi200Repository;
import com.seokjin.spring.springBoot.service.GetKospi200;
import com.seokjin.spring.springBoot.service.thread.SharedObject;
import com.seokjin.spring.springBoot.service.thread.ThreadPoolCustom;
import com.seokjin.spring.springBoot.service.thread.task.SaveKospiData;

@Service
public class GetKospi200Impl  implements GetKospi200{
    
    @Autowired
    ThreadPoolCustom pool;
    
    @Autowired
    Kospi200Repository kospiDB;
    
    @Autowired
    JdbcTemplate jdTemplate;
    
    @Override
    public void putKospiTotalDataIntoDB() {
        putKospiDataIntoDB(1313);
    }

    @Override
    public void putKospiDataIntoDB(int pageNumber) {
        pool.getPool(10);
        for (int index = 1; index <= pageNumber; index++) {
            String url = "https://finance.naver.com/sise/sise_index_day.nhn";
            Map<String, String> params = new HashMap<String, String>();
            params.put("code","KOSPI");
            params.put("page",String.valueOf(index));
            
            url = HttpClientCustom.getParamToString(url, params);
            
            SharedObject<String> shared = new SharedObject<String>(url);
            SaveKospiData saveKospi = new SaveKospiData(shared, jdTemplate);
            saveKospi.setShared(shared);
            
            pool.execute(saveKospi);
            
        }
        pool.destroy();
    }
}

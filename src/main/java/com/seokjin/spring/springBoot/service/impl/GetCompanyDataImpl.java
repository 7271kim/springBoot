package com.seokjin.spring.springBoot.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.seokjin.kim.library.HttpClientCustom;
import com.seokjin.spring.springBoot.service.GetCompanyData;
import com.seokjin.spring.springBoot.service.thread.SharedObject;
import com.seokjin.spring.springBoot.service.thread.ThreadPoolCustom;
import com.seokjin.spring.springBoot.service.thread.task.SaveKospi200Data;
import com.seokjin.spring.springBoot.service.thread.task.SaveKospiData;

@Service
public class GetCompanyDataImpl  implements GetCompanyData{
    
    @Autowired
    ThreadPoolCustom pool;
    
    @Autowired
    JdbcTemplate jdTemplate;
    
    @Override
    public void getKospi200Code() {
        getKospi200Code(4);
    }

    @Override
    public void getKospi200Code( int pageNumber ) {
        pool.getPool(10);
        for (int index = 1; index <= pageNumber; index++) {
            String url = "https://finance.naver.com/sise/sise_market_sum.nhn";
            Map<String, String> params = new HashMap<String, String>();
            params.put("page",String.valueOf(index));
            
            url = HttpClientCustom.getParamToString(url, params);
            
            SharedObject<String> shared = new SharedObject<String>(url);
            SaveKospi200Data saveKospi200Data = new SaveKospi200Data(shared, jdTemplate);
            saveKospi200Data.setShared(shared);
            
            pool.execute(saveKospi200Data);
            
        }
        pool.destroy();
    }

}

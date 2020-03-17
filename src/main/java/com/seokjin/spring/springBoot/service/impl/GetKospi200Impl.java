package com.seokjin.spring.springBoot.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seokjin.kim.library.HttpClientCustom;
import com.seokjin.spring.springBoot.service.GetKospi200;
import com.seokjin.spring.springBoot.service.thread.SharedObject;
import com.seokjin.spring.springBoot.service.thread.ThreadPoolCustom;
import com.seokjin.spring.springBoot.service.thread.task.SaveKospiData;

@Service
public class GetKospi200Impl  implements GetKospi200{
    
    @Autowired
    ThreadPoolCustom pool;
    
    @Autowired
    SharedObject<String> shared;
    
    @Autowired
    SaveKospiData saveKospi;
    
    @Override
    public void putKospi200DataIntoDB() {
        pool.getPool(10);
        for (int index = 1; index <= 10; index++) {
            String url = "https://finance.naver.com/sise/sise_index_day.nhn";
            Map<String, String> params = new HashMap<String, String>();
            params.put("code","KOSPI");
            params.put("page",String.valueOf(index));
            
            url = HttpClientCustom.getParamToString(url, params);
            
            shared.setShared(url);
            
            saveKospi.setShared(shared);
            
            pool.execute(saveKospi);
            
        }
        pool.destroy();
    }
}

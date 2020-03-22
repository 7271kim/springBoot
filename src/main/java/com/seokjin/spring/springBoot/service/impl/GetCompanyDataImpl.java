package com.seokjin.spring.springBoot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.seokjin.kim.library.HttpClientCustom;
import com.seokjin.kim.library.StringToEveryThing;
import com.seokjin.spring.springBoot.jpa.CompanyDefault;
import com.seokjin.spring.springBoot.jpa.KospiModel;
import com.seokjin.spring.springBoot.service.GetCompanyData;
import com.seokjin.spring.springBoot.service.thread.SharedObject;
import com.seokjin.spring.springBoot.service.thread.ThreadPoolCustom;
import com.seokjin.spring.springBoot.service.thread.task.SaveCcompanyData;
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
    
    @Override
    public void getCompanyAll( int number) {
        pool.getPool(10);
        
        String nameDB = CompanyDefault.class.getName().replace(KospiModel.class.getPackageName(), "").replace(".","");
        nameDB = StringToEveryThing.getUpperCaseStringToLowercaseWithWant(nameDB, "_");
        String quryOne = "SELECT * FROM "+nameDB;
        List<CompanyDefault>  oneData = jdTemplate.query(quryOne, new BeanPropertyRowMapper<CompanyDefault>(CompanyDefault.class));
        
        for (int index = 0; index < oneData.size(); index++) {
            CompanyDefault companyDefault = oneData.get(index);
            for (int indexPage = 1; indexPage <= number; indexPage++) {
                String url = "https://finance.naver.com/item/frgn.nhn";
                Map<String, String> params = new HashMap<String, String>();
                String companyCome = companyDefault.getCompanyCode();
                params.put("page",String.valueOf(indexPage));
                params.put("code",companyCome);
                url = HttpClientCustom.getParamToString(url, params);
                
                Map<String, String> shareMap = new HashMap<String, String>();
                shareMap.put("url",url);
                shareMap.put("code",companyCome);
                
                SharedObject<Map<String, String>> shared = new SharedObject<Map<String, String>>(shareMap);
                
                
                SaveCcompanyData saveCcompanyData = new SaveCcompanyData(shared, jdTemplate);
                
                pool.execute(saveCcompanyData);
            }
        }
        pool.destroy();
    }

}

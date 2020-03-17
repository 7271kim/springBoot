package com.seokjin.spring.springBoot.service.thread.task;

import java.text.NumberFormat;
import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seokjin.kim.library.JsoupCustom;
import com.seokjin.kim.library.StringToEveryThing;
import com.seokjin.spring.springBoot.jpa.Kospi200Model;
import com.seokjin.spring.springBoot.jpa.repository.Kospi200Repository;
import com.seokjin.spring.springBoot.service.thread.SharedObject;
import com.seokjin.spring.springBoot.service.thread.ThreadPoolCustom;

@Service
public class SaveKospiDataDB implements Runnable {
    
    @Autowired
    Kospi200Repository kospiDB;
    
    
    private SharedObject<Kospi200Model> shared;
    
    public SaveKospiDataDB() {}
    
    public SaveKospiDataDB( SharedObject<Kospi200Model> shared ) {
        this.shared = shared;
    }
    

    public SharedObject<Kospi200Model> getShared() {
        return shared;
    }


    public void setShared(SharedObject<Kospi200Model> shared) {
        this.shared = shared;
    }
    
    @Autowired
    ThreadPoolCustom pool;


    @Override
    public void run() {
        Kospi200Model kospi = shared.getShared();
        pool.execute(()->{
            kospiDB.save(kospi);
        });
        
    }

}

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
public class SaveKospiData implements Runnable {
    
    @Autowired
    Kospi200Repository kospiDB;
    
    @Autowired
    SaveKospiDataDB saveKospiDB;
    
    private SharedObject<String> shared;
    
    public SaveKospiData() {}
    
    public SaveKospiData( SharedObject<String> shared ) {
        this.shared = shared;
    }
    

    public SharedObject<String> getShared() {
        return shared;
    }


    public void setShared(SharedObject<String> shared) {
        this.shared = shared;
    }
    
    @Autowired
    ThreadPoolCustom pool;


    @Override
    public void run() {
        String url = shared.getShared();
        
        Document doc = JsoupCustom.getGetDocumentFromURL(url);
        Elements trData = doc.select(".box_type_m table:nth-of-type(1) tr");
        for (int index = 0; index < trData.size(); index++) {
            Element tr  = trData.get(index);
            Elements td = tr.select("td.date");
            
            if( td.size() > 0 ) {
                
                String dateString = tr.select("td:nth-of-type(1)").text();
                Date date = StringToEveryThing.getStringToDate(dateString, "yyyy-mm-dd");
                
                Double todayPrice = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(2)").text()).doubleValue();
                
                boolean isDown =   tr.select("td:nth-of-type(3) span").attr("class").indexOf("nv") > -1 ? true : false; 
                
                Double upDownSize = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(3)").text()).doubleValue();
                upDownSize = isDown ? upDownSize*-1 : upDownSize;
                
                Double percentage = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(4)").text()).doubleValue();
                percentage = isDown ? upDownSize*-1 : upDownSize;
                
                Double volumn = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(5)").text()).doubleValue();
                
                Kospi200Model kospi = new Kospi200Model(date, todayPrice, upDownSize, percentage, volumn);
                
                
                SharedObject<Kospi200Model> asdads = new SharedObject<Kospi200Model>();
                System.out.println(dateString);
                asdads.setShared(kospi);
                saveKospiDB.setShared(asdads);
                kospiDB.save(kospi);
                //pool.execute(saveKospiDB);
            }
            
        }
    }

}

package com.seokjin.spring.springBoot.service.thread.task;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.seokjin.kim.library.JsoupCustom;
import com.seokjin.kim.library.StringToEveryThing;
import com.seokjin.spring.springBoot.jpa.KospiModel;
import com.seokjin.spring.springBoot.service.thread.SharedObject;


@Service
public class SaveKospiData implements Runnable {
    
    private SharedObject<String> shared;
    private JdbcTemplate jdTemplate;
    
    public SaveKospiData() {}
    
    public SaveKospiData( SharedObject<String> shared , JdbcTemplate jdTemplate ) {
        this.shared = shared;
        this.jdTemplate = jdTemplate;
    }
    

    public SharedObject<String> getShared() {
        return shared;
    }


    public void setShared(SharedObject<String> shared) {
        this.shared = shared;
    }
    

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
                String date = StringToEveryThing.getStringToDateDash(dateString);
                
                Double todayPrice = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(2)").text()).doubleValue();
                
                boolean isDown =   tr.select("td:nth-of-type(3) span").attr("class").indexOf("nv") > -1 ? true : false; 
                
                Double upDownSize = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(3)").text()).doubleValue();
                upDownSize = isDown ? upDownSize*-1 : upDownSize;
                
                Double percentage = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(4)").text()).doubleValue();
                
                Double volumn = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(5)").text()).doubleValue();
                
                //Kospi200Model kospi = new Kospi200Model(date, todayPrice, upDownSize, percentage, volumn);
                String nameDB = KospiModel.class.getName().replace(KospiModel.class.getPackageName(), "").replace(".","");
                nameDB = StringToEveryThing.getUpperCaseStringToLowercaseWithWant(nameDB, "_");
                
                if ( StringUtils.isNoneBlank(dateString) ) {
                    String quryInsert = "INSERT INTO "+nameDB+"(date,percentage,today_price,up_down_size,volumn) VALUES ( ? , ? , ? , ? , ? )";
                    String quryUpdate = "UPDATE "+nameDB+" SET percentage = ?, today_price = ?, up_down_size = ?, volumn = ? WHERE date = ? ";
                    String quryOne = "SELECT * FROM "+nameDB+" WHERE date = ?";
                    System.out.println(date);
                    try {
                        List<KospiModel>  oneData = jdTemplate.query(quryOne, new BeanPropertyRowMapper<KospiModel>(KospiModel.class), date);
                        if( oneData.size() > 0 ) {
                            jdTemplate.update(quryUpdate, percentage,todayPrice,upDownSize,volumn, date );
                        } else {
                            jdTemplate.update(quryInsert,date,percentage,todayPrice,upDownSize,volumn);
                        }
                        
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                
                //kospiDB.save(kospi);
            }
            
        }
    }

}

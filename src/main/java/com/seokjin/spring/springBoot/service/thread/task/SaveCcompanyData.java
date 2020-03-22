package com.seokjin.spring.springBoot.service.thread.task;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.seokjin.kim.library.JsoupCustom;
import com.seokjin.kim.library.StringToEveryThing;
import com.seokjin.spring.springBoot.jpa.Company;
import com.seokjin.spring.springBoot.jpa.KospiModel;
import com.seokjin.spring.springBoot.service.thread.SharedObject;


@Service
public class SaveCcompanyData implements Runnable {
    
    private SharedObject<Map<String, String>> shared;
    private JdbcTemplate jdTemplate;
    
    public SaveCcompanyData() {}
    
    public SaveCcompanyData( SharedObject<Map<String, String>> shared , JdbcTemplate jdTemplate ) {
        this.shared = shared;
        this.jdTemplate = jdTemplate;
    }
    

    public SharedObject<Map<String, String>> getShared() {
        return shared;
    }


    public void setShared(SharedObject<Map<String, String>> shared) {
        this.shared = shared;
    }
    

    @Override
    public void run() {
        Map<String, String> shardMap = shared.getShared();
        String url = shardMap.get("url");
        String code = shardMap.get("code");
        Document doc = JsoupCustom.getGetDocumentFromURL(url);
        Elements trData = doc.select(".inner_sub > table:nth-of-type(1) tr");
        for (int index = 0; index < trData.size(); index++) {
            Element tr  = trData.get(index);
            Elements td = tr.select("td.tc");
            
            if( td.size() > 0 ) {
                
                String dateString = tr.select("td:nth-of-type(1)").text();
                String date = StringToEveryThing.getStringToDateDash(dateString);
                Double todayPrice = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(2)").text()).doubleValue();
                
                boolean isDown =   tr.select("td:nth-of-type(3) span").attr("class").indexOf("nv") > -1 ? true : false; 
                
                Double upDownSize = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(3)").text()).doubleValue();
                upDownSize = isDown ? upDownSize*-1 : upDownSize;
                
                Double percentage = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(4)").text()).doubleValue();
                
                Double volumn = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(5)").text()).doubleValue();
                
                Double companyNetSales = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(6)").text()).doubleValue();
                Double forignNetSales = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(7)").text()).doubleValue();
                Double individualNetSales = ( forignNetSales + companyNetSales ) * -1;
                Double forignretentionRate = StringToEveryThing.getStringToDouble(tr.select("td:nth-of-type(9)").text()).doubleValue();
                
                //Kospi200Model kospi = new Kospi200Model(date, todayPrice, upDownSize, percentage, volumn);
                String nameDB = Company.class.getName().replace(Company.class.getPackageName(), "").replace(".","");
                nameDB = StringToEveryThing.getUpperCaseStringToLowercaseWithWant(nameDB, "_");
               
                if ( StringUtils.isNoneBlank(dateString) ) {
                    String quryInsert = "INSERT INTO "+nameDB+"(date, company_default_company_code, company_net_sales, forign_net_sales , forignretention_rate, individual_net_sales, percentage, today_price, up_down_size, volumn) VALUES ( ? , ? , ?, ? ,? , ? , ? , ? , ? , ?)";
                    String quryUpdate = "UPDATE "+nameDB+" SET company_net_sales = ?, forign_net_sales = ?, forignretention_rate = ? , individual_net_sales = ? , percentage = ? , today_price = ?, up_down_size = ? , volumn = ?  WHERE date = ? AND company_default_company_code = ? ";
                    String quryOne = "SELECT * FROM "+nameDB+" WHERE date = ? AND company_default_company_code = ?";
                    List<Map<String, Object>>  oneData =  jdTemplate.queryForList(quryOne,date,code);
                    try {
                        if( oneData.size() > 0 ) {
                            jdTemplate.update(quryUpdate,companyNetSales, forignNetSales, forignretentionRate, individualNetSales, percentage, todayPrice, upDownSize, volumn, date, code);
                        } else {
                            jdTemplate.update(quryInsert, date,code, companyNetSales, forignNetSales, forignretentionRate, individualNetSales, percentage, todayPrice, upDownSize, volumn);
                        }
                        
                    } catch (Exception e) {
                        System.out.println(oneData.size() > 0 );
                        System.out.println(e.getMessage());
                    }
                }
                
                //kospiDB.save(kospi);
            }
            
        }
    }

}

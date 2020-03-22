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
import com.seokjin.spring.springBoot.jpa.CompanyDefault;
import com.seokjin.spring.springBoot.jpa.KospiModel;
import com.seokjin.spring.springBoot.service.thread.SharedObject;


@Service
public class SaveKospi200Data implements Runnable {
    
    private SharedObject<String> shared;
    private JdbcTemplate jdTemplate;
    
    public SaveKospi200Data() {}
    
    public SaveKospi200Data( SharedObject<String> shared , JdbcTemplate jdTemplate ) {
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
        Elements trData = doc.select(".box_type_l table:nth-of-type(1) tr");
        
        for (int index = 0; index < trData.size(); index++) {
            Element tr  = trData.get(index);
            Elements td = tr.select("td.no");
            
            if( td.size() > 0 ) {
                
                String rankString = tr.select("td:nth-of-type(1)").text();
                String companyCode =  StringToEveryThing.getLastStringWithSeperator( tr.select("td:nth-of-type(2) a").attr("href"), "=" );
                String companyName = tr.select("td:nth-of-type(2)").text();
                
                String nameDB = CompanyDefault.class.getName().replace(KospiModel.class.getPackageName(), "").replace(".","");
                nameDB = StringToEveryThing.getUpperCaseStringToLowercaseWithWant(nameDB, "_");
                
                 if ( StringUtils.isNoneBlank(rankString) ) {
                    int rank =  Integer.parseInt( tr.select("td:nth-of-type(1)").text() );
                    String quryInsert = "INSERT INTO "+nameDB+"(company_code,company_name,rank) VALUES ( ? , ? , ? )";
                    String quryUpdate = "UPDATE "+nameDB+" SET company_name = ?, rank = ? WHERE company_code = ? ";
                    String quryOne = "SELECT * FROM "+nameDB+" WHERE company_code = ?";
                    System.out.println(companyName);
                    try {
                        List<CompanyDefault>  oneData = jdTemplate.query(quryOne, new BeanPropertyRowMapper<CompanyDefault>(CompanyDefault.class), companyCode);
                        if( oneData.size() > 0 ) {
                            jdTemplate.update(quryUpdate,companyName,rank, companyCode );
                        } else {
                            jdTemplate.update(quryInsert, companyCode, companyName, rank );
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

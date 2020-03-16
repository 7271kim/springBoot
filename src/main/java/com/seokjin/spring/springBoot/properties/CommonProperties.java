package com.seokjin.spring.springBoot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("common")
public class CommonProperties {
    private String name;
    private int howLong;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getHowLong() {
        return howLong;
    }
    public void setHowLong(int howLong) {
        this.howLong = howLong;
    }
    
}

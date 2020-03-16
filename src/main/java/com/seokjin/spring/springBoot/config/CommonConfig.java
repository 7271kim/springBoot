package com.seokjin.spring.springBoot.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.seokjin.spring.springBoot.model.CommonPropertiesModel;
import com.seokjin.spring.springBoot.properties.CommonProperties;

@Configuration
@EnableConfigurationProperties(CommonProperties.class)
public class CommonConfig {
    
    @Bean
    @ConditionalOnMissingBean
    public CommonPropertiesModel getCommonProperties( CommonProperties properties ) {
        CommonPropertiesModel commonPropertiesModel = new CommonPropertiesModel();
        commonPropertiesModel.setHowLong(properties.getHowLong());
        commonPropertiesModel.setName(properties.getName());
        return commonPropertiesModel;
    }
}

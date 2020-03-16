package com.seokjin.spring.springBoot.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner  {

    @PersistenceContext
    EntityManager entitymanager;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setName("김석진");
        account.setPassword("aaaaaa");
        
        entitymanager.persist(account);
    }
    
    

}

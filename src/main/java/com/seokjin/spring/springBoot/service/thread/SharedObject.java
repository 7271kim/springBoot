package com.seokjin.spring.springBoot.service.thread;

import org.springframework.stereotype.Service;

@Service
public class SharedObject <T> {
    T shared;
   
    public SharedObject(){}
    
    public SharedObject(T shared) {
        this.shared = shared;
    }

    public T getShared() {
        return shared;
    }

    public void setShared(T shared) {
        this.shared = shared;
    }

}

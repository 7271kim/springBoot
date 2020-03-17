package com.seokjin.spring.springBoot.service.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

@Service
public class ThreadPoolCustom {
    private ExecutorService pool;

    public ExecutorService getPool( int count ) {
        this.pool = Executors.newFixedThreadPool(count);
        return pool;
    }
    
    public void execute( Runnable task ) {
        pool.execute(task);
    }
    
    public void destroy() {
        pool.shutdown();
    }
    
}

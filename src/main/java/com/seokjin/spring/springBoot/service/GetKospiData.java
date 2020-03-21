package com.seokjin.spring.springBoot.service;

public interface GetKospiData {
    /**
     * 파싱을 통해 데이터 전체 Kospi 1990-1-04일부터 현재까지 데이터 DB에 넣는 작업
     * @param 
     * @return 
     */
    public void getKospiTotalDataIntoDB();
    public void getKospiTodayDataIntoDB(int pageNumber);
    
}

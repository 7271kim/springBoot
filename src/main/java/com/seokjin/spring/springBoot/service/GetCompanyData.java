package com.seokjin.spring.springBoot.service;

public interface GetCompanyData {
    
    /**
     * 파싱을 통해 데이터 전체 시가총액 상위 200개 종목 회사코드와 회사명 가지고 오기
     * @param 
     * @return 
     */
    public void getKospi200Code();
    public void getKospi200Code( int number);
    
}

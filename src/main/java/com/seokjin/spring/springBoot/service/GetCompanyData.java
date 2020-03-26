package com.seokjin.spring.springBoot.service;

public interface GetCompanyData {
    
    /**
     * 파싱을 통해 데이터 전체 시가총액 상위 200개 종목 회사코드와 회사명 가지고 오기
     * @param 
     * @return 
     */
    public void getKospi200Code();
    public void getKospi200Code( int number);
    
    /**
     * 개별 종목 모든 주식 데이터 가지고 오기
     * @param 
     * @return 
     */
    public void getCompanyAll( int number);
    
    /**
     * Dart가 제공하는 xml파일에서 code부분 가지고 와서 세팅 
     * @param 
     * @return 
     */
    public void setCompanyDartCode();
}

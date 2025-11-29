package com.example.kiosk.member.service;

public interface MemberService {
    //회원가입하는 기능
    boolean registerMember(String phoneNumber);

    //스탬프 적립하는 기능(음료 1개 당 스탬프 1개 적립, 10개를 적립하면 할인 쿠폰 1개 발급)
    Integer saveStamp(Long orderId, String phoneNumber);

}
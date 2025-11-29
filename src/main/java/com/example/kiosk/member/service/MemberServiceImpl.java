package com.example.kiosk.member.service;

import com.example.kiosk.member.entity.Member;
import com.example.kiosk.order.entity.OrderItem;
import com.example.kiosk.order.exception.OrderNotFoundException;
import com.example.kiosk.member.repository.MemberRepository;
import com.example.kiosk.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public boolean registerMember(String phoneNumber) {
        if(memberRepository.existsMemberByPhoneNumber(phoneNumber)){
            return false;
        }
        Member member = new Member(phoneNumber,0,0);
        memberRepository.save(member);
        return true;
    }

    @Override
    public Integer saveStamp(Long orderId, String phoneNumber) {
        if (memberRepository.existsMemberByPhoneNumber(phoneNumber)){
            Member member = memberRepository.findMemberByPhoneNumber(phoneNumber);
            Integer totalQuantity = 0;
            List<OrderItem> orderItemList = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new).getOrderItemList();
            for(OrderItem orderItem : orderItemList){
                totalQuantity += orderItem.getQuantity();
            }
            member.setStamp(member.getStamp() + totalQuantity);
            Integer couponCount = member.getStamp()/10;
            if(member.getStamp() >= 10){
                member.setCoupon(member.getCoupon()+ couponCount);
                member.setStamp(member.getStamp()%10);
            }
            memberRepository.save(member);
            return totalQuantity;
        }else {
            //회원으로 등록되지 않은 전화번호일 경우
            return 0;
        }
    }
}

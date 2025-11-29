package com.example.kiosk.coupon.service;

import com.example.kiosk.receipt.exception.ReceiptNotFoundException;
import com.example.kiosk.member.entity.Member;
import com.example.kiosk.member.repository.MemberRepository;
import com.example.kiosk.receipt.entity.Receipt;
import com.example.kiosk.receipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private MemberRepository  memberRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public Integer findCoupon(String phoneNumber) {
        return memberRepository.findMemberByPhoneNumber(phoneNumber).getCoupon();
    }

    @Override
    public int useCoupon(Long receiptId, boolean isUsed, String phoneNumber, Integer count) {
        //고객이 쿠폰 사용 선택창에서 '아니오'를 선택할 경우 0을 반환
        if (!isUsed){
            return 0;
        }
        Member member = memberRepository.findMemberByPhoneNumber(phoneNumber);
        Receipt receipt = receiptRepository.findById(receiptId).orElseThrow(ReceiptNotFoundException::new);
        //-1을 받으면 사용하려는 쿠폰 개수보다 보유 쿠폰수가 적다는 의미
        if(member.getCoupon() < count){
            return -1;
        }
        member.setCoupon(member.getCoupon()-count);
        memberRepository.save(member);
        //쿠폰 사용 시 영수증에 할인금액이 적용된 결제금액으로 저장
        receipt.setUsedCoupon(count);
        receipt.setFinalCost();
        receiptRepository.save(receipt);

        return count;
    }
}

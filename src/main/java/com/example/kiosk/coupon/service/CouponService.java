package com.example.kiosk.coupon.service;

public interface CouponService {
    Integer findCoupon(String phoneNumber);

    int useCoupon(Long receiptId, boolean isUsed, String phoneNumber, Integer count);
}

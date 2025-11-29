package com.example.kiosk.coupon.controller;

import com.example.kiosk.coupon.service.CouponService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping("/select")
    public String useCouponView(){
        return "kiosk/coupon/select";
    }

    @PostMapping("/select")
    public String useCouponForDiscount(
            HttpSession session, Model model,
            @RequestParam boolean isUsed, @RequestParam Integer count) {

        Long orderId = (Long)session.getAttribute("orderId");
        String telephoneNumber = (String)session.getAttribute("telephoneNumber");
        int usedCoupon = couponService.useCoupon(orderId, isUsed, telephoneNumber, count);

        switch (usedCoupon) {
            case 0 -> model.addAttribute("message","쿠폰 사용하지 않음을 선택하셨습니다");
            case -1 -> model.addAttribute("message","사용하려는 쿠폰보다 보유한 쿠폰이 부족합니다");
            default -> model.addAttribute("message",usedCoupon + "개 쿠폰 사용을 선택하셨습니다");
        };
        return "kiosk/coupon/result";
    }

}

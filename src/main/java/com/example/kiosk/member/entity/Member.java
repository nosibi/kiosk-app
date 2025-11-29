package com.example.kiosk.member.entity;

import com.example.global.validation.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//스탬프 적립 회원
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private Integer stamp;

    private Integer coupon;

    public Member(String phoneNumber, Integer stamp, Integer coupon) {
        this.phoneNumber = phoneNumber;
        this.stamp = stamp;
        this.coupon = coupon;
    }
}
package com.example.kiosk.order.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품 기본 정보
    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Integer itemPrice;

    @Column(nullable = false)
    private Integer quantity;

    //추가옵션
    private boolean addIce;
    private boolean addShot;
    private boolean addSugar;

    //Order와의 관계(N:1)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;
}

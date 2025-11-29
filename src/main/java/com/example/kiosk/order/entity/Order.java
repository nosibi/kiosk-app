package com.example.kiosk.order.entity;

import com.example.global.BasedEntity;
import com.example.kiosk.receipt.entity.Receipt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "order_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BasedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean takeOut;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToOne(mappedBy = "order",  cascade = CascadeType.ALL, orphanRemoval = true)
    private Receipt receipt;

    //편의 메서드
    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        //양방향 관계 설정
        orderItem.setOrder(this);
    }
}
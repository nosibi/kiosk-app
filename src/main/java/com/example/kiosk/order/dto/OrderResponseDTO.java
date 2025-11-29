package com.example.kiosk.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//결제 전 주문 상품, 할인 금액(쿠폰 사용), 결제할 금액 정보
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private boolean takeOut;
    private Integer OrderNumber;
    private List<OrderItemResponseDTO>  orderItemDTOList = new ArrayList<>();
    private Integer discountPrice;
    private Integer usedCoupon;
    private Integer cost; //할인 금액이 적용된 최종 결제 금액
}

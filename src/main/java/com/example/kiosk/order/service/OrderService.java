package com.example.kiosk.order.service;

import com.example.kiosk.order.dto.OrderItemDTO;
import com.example.kiosk.order.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    //n개의 상품의 옵션을 선택하여 주문서에 저장하는 기능
    void makeOrder(boolean isTakeOut, List<OrderItemDTO> orderItemDTOs);

    //가장 최근에 생성된 주문서를 가져오는 기능
    Long findRecentOrderId();

    //하루 기준 주문서 전체 조회
    List<OrderResponseDTO> findAllToday();
}

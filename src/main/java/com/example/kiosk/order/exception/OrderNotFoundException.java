package com.example.kiosk.order.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
      super("번호에 해당하는 주문 내역이 없습니다");
    }
}
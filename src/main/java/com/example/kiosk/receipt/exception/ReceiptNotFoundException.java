package com.example.kiosk.receipt.exception;

public class ReceiptNotFoundException extends RuntimeException {
  public ReceiptNotFoundException() {
    super("해당 주문번호에 일치하는 영수증이 존재하지 않습니다");
  }
}
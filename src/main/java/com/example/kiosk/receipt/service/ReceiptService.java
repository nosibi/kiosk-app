package com.example.kiosk.receipt.service;

import com.example.kiosk.receipt.dto.ReceiptResponseDTO;

public interface ReceiptService {
    //영수증 출력
    ReceiptResponseDTO findReceipt(Long orderId);

    //영수증 미출력(대기번호표만 출력)
    String getQueueTicket(Long orderId);
}
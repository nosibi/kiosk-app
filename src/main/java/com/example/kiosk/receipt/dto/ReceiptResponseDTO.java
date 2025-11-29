package com.example.kiosk.receipt.dto;

import com.example.kiosk.order.dto.OrderItemResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class ReceiptResponseDTO {
    List<OrderItemResponseDTO>  items;
    Integer cost;
    Integer waitingNumber;
}
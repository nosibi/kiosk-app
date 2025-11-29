package com.example.kiosk.order.dto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private String itemName;
    private Integer itemPrice;
    private Integer quantity;
    private boolean addIce;
    private boolean addShot;
    private boolean addSugar;
}

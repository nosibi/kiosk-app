package com.example.kiosk.order.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//상품의 정보
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    //기본 선택 사항(필수)
    //Menu 도메인으로부터 정보를 가져오기 위한 상품 id
    private Long itemId;

    private String itemName;

    @Min(value = 0)
    private Integer quantity;

    //추가옵션
    @Min(value = 0)
    private boolean addIce;
    @Min(value = 0)
    private boolean addShot;
    @Min(value = 0)
    private boolean addSugar;
}
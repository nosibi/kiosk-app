package com.example.kiosk.menu.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class MenuRequestDTO {

    @NotBlank(message = "상품명 입력은 필수입니다")
    String name;

    @Range(min = 1000, max = 6000, message = "1000원 이상 6000원 이하로 가격을 설정하십시오")
    Integer price;

    @Column(nullable = false)
    String category;
}

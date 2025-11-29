package com.example.kiosk.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MenuResponseDTO {
    private Long id;
    private String name;
    private Integer price;
    private boolean isSoldOut;
    private String category;
}

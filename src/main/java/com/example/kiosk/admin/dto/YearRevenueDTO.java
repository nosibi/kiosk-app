package com.example.kiosk.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YearRevenueDTO {
    private Integer month;
    private Integer revenue = 0;

    public YearRevenueDTO(Integer month, Integer revenue) {
        this.month = month;
        this.revenue = revenue;
    }
}

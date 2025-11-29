package com.example.kiosk.admin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MonthlyRevenueDTO {
    private LocalDate date;
    private Integer revenue;

    public MonthlyRevenueDTO(LocalDate date, Integer revenue) {
        this.date = date;
        this.revenue = revenue;
    }
}

package com.example.kiosk.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyRevenueResult {
    private List<MonthlyRevenueDTO> monthlyRevenueList;
    private Integer totalRevenue;
}

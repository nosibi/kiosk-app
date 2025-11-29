package com.example.kiosk.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class YearRevenueResult {
    private List<YearRevenueDTO> yearRevenueList;
    private Integer totalRevenue = 0;
}

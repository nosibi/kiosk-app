package com.example.kiosk.admin.service;

import com.example.kiosk.admin.dto.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AdminService {
    //로그인 요청 후 관리자 계정 조회
    public AdminResponseDTO findMember(AdminRequestDTO adminRequestDTO);

    //관리자 등록
    public AdminResponseDTO registerAdmin(AdminRequestDTO adminRequestDTO);

    //특정 일자 영수증 목록에서 매출액 산출
    public Integer calculateRevenue(LocalDate localDate);

    //특정 달에 해당 하는 영수증 목록에서 매출액과 날짜 산출
    public MonthlyRevenueResult calculateMonthRevenue(String yearMonth);

    //특정 년도의 모든 월의 각 매출액을 산출
    public YearRevenueResult calculateYearRevenue(String yearData);
}

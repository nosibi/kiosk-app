package com.example.kiosk.admin.service;

import com.example.kiosk.admin.dto.*;
import com.example.kiosk.admin.entity.Admin;
import com.example.kiosk.admin.repository.AdminRepository;
import com.example.kiosk.receipt.entity.Receipt;
import com.example.kiosk.receipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public AdminResponseDTO findMember(AdminRequestDTO adminRequestDTO) {
        Admin findAdmin = adminRepository
                .getAdminByLoginId(adminRequestDTO.getLoginID())
                .filter(admin -> admin.getPassword().equals(adminRequestDTO.getPassword()))
                .filter(admin -> admin.getAdminLocation().equals(adminRequestDTO.getAdminLocation()))
                .orElse(null);
        if(findAdmin == null){
            return null;
        }else {
            AdminResponseDTO adminResponseDTO = new AdminResponseDTO();
            adminResponseDTO.setAdminLocation(findAdmin.getAdminLocation());
            adminResponseDTO.setLoginID(findAdmin.getLoginId());
            adminResponseDTO.setPassword(findAdmin.getPassword());
            return adminResponseDTO;
        }
    }

    @Override
    public AdminResponseDTO registerAdmin(AdminRequestDTO adminRequestDTO) {
        Admin admin = new Admin();
        admin.setLoginId(adminRequestDTO.getLoginID());
        admin.setPassword(adminRequestDTO.getPassword());
        admin.setAdminLocation(adminRequestDTO.getAdminLocation());
        Admin registeredAdmin = adminRepository.registerAdmin(admin);
        AdminResponseDTO adminResponseDTO = new AdminResponseDTO();
        adminResponseDTO.setAdminLocation(registeredAdmin.getAdminLocation());
        adminResponseDTO.setLoginID(registeredAdmin.getLoginId());
        adminResponseDTO.setPassword(registeredAdmin.getPassword());
        return adminResponseDTO;
    }

    @Override
    public Integer calculateRevenue(LocalDate localDate) {
        //LocalDate -> LocalDateTime으로 변환
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
        List<Receipt> receiptList = receiptRepository.findAllByCreatedAtBetween(startOfDay, endOfDay);
        Integer totalCost = 0;
        for (Receipt receipt : receiptList) {
            totalCost +=  receipt.getCost();
        }
        return totalCost;
    }

    @Override
    public MonthlyRevenueResult calculateMonthRevenue(String yearMonth) {
        String[] split = yearMonth.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); //윤년을 고려하여 달의 마지막 날짜 설정

        LocalDateTime startOfDay = startDate.atStartOfDay(); //해당 달의 시작
        LocalDateTime endOfDay = endDate.atTime(23,59,59); //해당 달의 마지막

        List<Receipt> receiptList = receiptRepository.findAllByCreatedAtBetween(startOfDay, endOfDay);

        //날짜별 매출 Map 0으로 초기화
        Map<LocalDate, Integer> revenueMap = new LinkedHashMap<>(); //날짜 순서 유지를 위해 입력한 순서를 유지하는 LinkedHashMap 사용
        LocalDate cursor = startDate;
        while (!cursor.isAfter(endDate)){
            revenueMap.put(cursor, 0);
            cursor = cursor.plusDays(1);
        }

        Integer totalRevenue = 0;

        //Receipt에서 매출 누적
        for (Receipt receipt : receiptList) {
            LocalDate date = receipt.getCreatedAt().toLocalDate();
            Integer revenue = receipt.getCost();
            revenueMap.put(date, revenueMap.get(date) + revenue);
            totalRevenue += revenue;
        }

        MonthlyRevenueResult monthlyRevenueResult = new MonthlyRevenueResult();
        monthlyRevenueResult.setMonthlyRevenueList(revenueMap.entrySet().stream().map(entry
                -> new MonthlyRevenueDTO(entry.getKey(), entry.getValue())).collect(Collectors.toList()));
        monthlyRevenueResult.setTotalRevenue(totalRevenue);

        //DTO로 변환하여 날짜 순으로 유지된 데이터 반환
        return monthlyRevenueResult;
    }

    @Override
    public YearRevenueResult calculateYearRevenue(String yearData) {
        int year = Integer.parseInt(yearData);
        YearRevenueResult yearRevenueResult = new YearRevenueResult();
        List<YearRevenueDTO> yearRevenueDTOList = new ArrayList<>();

        //월 총 매출 합산액 계산(1~12월까지 적용)
        for(int i = 1; i <= 12; i++){
             LocalDate startOfMonth = LocalDate.of(year,i,1);
             LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

             LocalDateTime startOfDay = startOfMonth.atStartOfDay();
             LocalDateTime endOfDay = endOfMonth.atTime(23,59,59);

            YearRevenueDTO yearRevenueDTO = new YearRevenueDTO();

            List<Receipt> receiptList = receiptRepository.findAllByCreatedAtBetween(startOfDay, endOfDay);
            for (Receipt receipt : receiptList) {
                yearRevenueDTO.setRevenue(yearRevenueDTO.getRevenue() + receipt.getCost());
            }
            yearRevenueDTO.setMonth(i);
            yearRevenueDTOList.add(yearRevenueDTO);
        }
        for (YearRevenueDTO yearRevenueDTO : yearRevenueDTOList) {
            yearRevenueResult.setTotalRevenue(yearRevenueResult.getTotalRevenue() + yearRevenueDTO.getRevenue());
        }
        yearRevenueResult.setYearRevenueList(yearRevenueDTOList);

        return yearRevenueResult;
    }
}
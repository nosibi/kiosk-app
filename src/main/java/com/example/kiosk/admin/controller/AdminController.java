package com.example.kiosk.admin.controller;

import com.example.kiosk.admin.dto.*;
import com.example.kiosk.admin.service.AdminService;
import com.example.kiosk.menu.service.MenuService;
import com.example.kiosk.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    
    @Autowired
    private AdminService adminService;

    //로그인 화면
    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("admin",new AdminRequestDTO());
        return "kiosk/admin/login";
    }

    //로그인
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("admin") AdminRequestDTO adminRequestDTO, BindingResult bindingResult, Model model, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "kiosk/admin/login";
        }
        AdminResponseDTO admin = adminService.findMember(adminRequestDTO);

        if(admin == null){
            log.info("로그인 오류 발생");
            bindingResult.reject("AccountError", "아이디 또는 패스워드가 일치하지 않습니다");
            return "kiosk/admin/login";
        }
        model.addAttribute("loginAdmin", admin);
        HttpSession session = request.getSession(true);
        session.setAttribute("LOGIN_ADMIN", admin);
        session.setMaxInactiveInterval(1800); //세션 타임아웃 30분으로 설정
        return "kiosk/admin/main";
    }

    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/kiosk";
    }

    //관리자 메인 화면 복귀
    @GetMapping("/back")
    public String back(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("LOGIN_ADMIN") == null){
            return "redirect:/kiosk"; //타임아웃으로 세션 만료 시 시작 페이지로 리다이렉트
        }
        model.addAttribute("loginAdmin",session.getAttribute("LOGIN_ADMIN"));
        return "kiosk/admin/main";
    }

    //매출 확인 화면
    @GetMapping("/revenue")
    public String revenueMenu(){
        return "kiosk/admin/revenue";
    }

    //특정 날짜 매출 조회 화면 이동
    @GetMapping("/revenue/day/view")
    public String revenueDayView(){
        return "kiosk/admin/revenue/dayRevenueView";
    }

    //특정 날짜 매출 조회
    @GetMapping("/revenue/day")
    public String getRevenueDay(@RequestParam LocalDate localDate, Model model){
        Integer revenue = adminService.calculateRevenue(localDate);
        model.addAttribute("date", localDate);
        model.addAttribute("revenue", revenue);
        return "kiosk/admin/revenue/dayRevenue";
    }

    //월매출 조회 화면 이동
    @GetMapping("/revenue/month/view")
    public String revenueMonthView(){
        return "kiosk/admin/revenue/monthRevenueView";
    }

    //월매출 조회
    @GetMapping("/revenue/month")
    public String getRevenueMonth(@RequestParam String yearMonth, Model model){
        MonthlyRevenueResult monthlyRevenueResult = adminService.calculateMonthRevenue(yearMonth);
        model.addAttribute("dateRevenues", monthlyRevenueResult.getMonthlyRevenueList());
        model.addAttribute("totalRevenue", monthlyRevenueResult.getTotalRevenue());
        return  "kiosk/admin/revenue/monthRevenue";
    }

    //연매출 조회 화면 이동
    @GetMapping("revenue/year/view")
    public String revenueYearView(){
        return "kiosk/admin/revenue/yearRevenueView";
    }

    //연매출 조회
    @GetMapping("revenue/year")
    public String getRevenueYear(@RequestParam String year, Model model){
        YearRevenueResult yearRevenueResult = adminService.calculateYearRevenue(year);
        model.addAttribute("monthRevenues", yearRevenueResult.getYearRevenueList());
        model.addAttribute("totalRevenue", yearRevenueResult.getTotalRevenue());
        return "kiosk/admin/revenue/yearRevenue";
    }
}

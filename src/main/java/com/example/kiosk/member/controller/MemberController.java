package com.example.kiosk.member.controller;

import com.example.kiosk.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/save")
    public String enrollStamp(HttpSession session, @RequestParam String telephoneNumber, Model model) {

        Long orderId =  (Long) session.getAttribute("orderId");
        session.setAttribute("telephoneNumber", telephoneNumber);
        Integer savedStamp = memberService.saveStamp(orderId, telephoneNumber);

        if(savedStamp == 0){
            model.addAttribute("errors", "존재하지 않는 회원입니다.");
            return "kiosk/member/enrollStamp";
        }else {
            model.addAttribute("savedStamp", savedStamp);
            return "kiosk/member/saveStamp";
        }
    }

    @GetMapping("/save")
    public String enrollStampView(){
        return "kiosk/member/enrollStamp";
    }

    @PostMapping("/register")
    public String registerMember(@RequestParam String telephoneNumber, Model model) {
        boolean result = memberService.registerMember(telephoneNumber);
        if(!result){
            model.addAttribute("error","이미 등록된 회원입니다.");
            return "kiosk/member/register";
        }else {
            return "kiosk/member/registerSuccess";
        }
    }

    @GetMapping("/register")
    public String registerMemberView(){
        return "kiosk/member/register";
    }

}
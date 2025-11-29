package com.example.kiosk;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/kiosk")
public class ViewController {

    //초기화 컨트롤러
    @GetMapping
    public String startView(HttpSession session){
        session.removeAttribute("orderId");
        session.removeAttribute("cart");
        session.removeAttribute("receipt");
        session.removeAttribute("takeOut");
        return "kiosk/index";
    }

    @GetMapping("/category")
    public String categoryView(@RequestParam int takeOut, HttpSession session) {
        session.setAttribute("takeOut", takeOut);
        return "redirect:/menu/category/coffee";
    }

}
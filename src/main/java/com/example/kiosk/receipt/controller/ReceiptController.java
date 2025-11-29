package com.example.kiosk.receipt.controller;

import com.example.kiosk.receipt.service.ReceiptService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/receipt")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @GetMapping("/select")
    public String selectReceipt() {
        return "kiosk/receipt/select";
    }

    @PostMapping("/select")
    public String selectReceipt(@RequestParam boolean isPrint, HttpSession session, Model model) {
        Long orderId =  (Long) session.getAttribute("orderId");

        if(isPrint){
            model.addAttribute("receipt", receiptService.findReceipt(orderId));
            return "kiosk/receipt/print";
        }else {
            model.addAttribute("receipt",receiptService.getQueueTicket(orderId));
            return "kiosk/receipt/wait";
        }
    }

    @PostMapping("/print")
    public String printReceipt(HttpSession session) {
        session.removeAttribute("orderId");
        session.removeAttribute("cart");
        session.removeAttribute("receipt");
        session.removeAttribute("takeOut");
        return "kiosk/index";
    }
}
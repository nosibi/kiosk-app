package com.example.kiosk.order.controller;

import com.example.kiosk.order.dto.OrderItemDTO;
import com.example.kiosk.order.dto.OrderResponseDTO;
import com.example.kiosk.order.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public String saveView(@RequestParam Long menuId,
                           @RequestParam String menuName,
                           @RequestParam(defaultValue = "false") boolean addIce,
                           @RequestParam(defaultValue = "false") boolean addShot,
                           @RequestParam(defaultValue = "false") boolean addSugar,
                           @RequestParam Integer quantity,
                           HttpSession session){
        OrderItemDTO orderItem = new OrderItemDTO();
        orderItem.setItemId(menuId);
        orderItem.setItemName(menuName);
        orderItem.setQuantity(quantity);
        orderItem.setAddIce(addIce);
        orderItem.setAddShot(addShot);
        orderItem.setAddSugar(addSugar);

        List<OrderItemDTO> cart = (List<OrderItemDTO>)  session.getAttribute("cart");
        if(cart==null){
            cart = new ArrayList<OrderItemDTO>();
        }
        cart.add(orderItem);
        session.setAttribute("cart",cart);

        return "kiosk/order/saveLogo";
    }

    @PostMapping("/pay")
    public String payView(Model model,  HttpSession session){
        List<OrderItemDTO> cart = (List<OrderItemDTO>)  session.getAttribute("cart");
        if(cart==null){
            cart = new ArrayList<OrderItemDTO>();
        }
        model.addAttribute("cart",cart);
        return "kiosk/order/pay";
    }

    @PostMapping("/make")
    public String makeOrder(HttpSession session){
        List<OrderItemDTO> itemList = (List<OrderItemDTO>)session.getAttribute("cart");
        if(itemList==null){
            return "redirect:/menu/category/coffee";
        }
        boolean takeOut;
        if((int)session.getAttribute("takeOut") == 0){
            takeOut = false;
        }else {
            takeOut = true;
        }
        orderService.makeOrder(takeOut, itemList);
        session.setAttribute("orderId", orderService.findRecentOrderId());
        return "kiosk/member/stamp";
    }

    @GetMapping("/back")
    public String back(){
        return "redirect:/menu/category/coffee";
    }


    @GetMapping("/info")
    public String findOrderInDay(Model model){
        LocalDate today = LocalDate.now();
        List<OrderResponseDTO> orderResponseDTOList = orderService.findAllToday();
        model.addAttribute("orderList",orderResponseDTOList);
        model.addAttribute("today",today);
        return "kiosk/order/checkOrder";
    }

}

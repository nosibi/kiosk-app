package com.example.kiosk.menu.controller;

import com.example.kiosk.menu.dto.MenuRequestDTO;
import com.example.kiosk.menu.dto.MenuResponseDTO;
import com.example.kiosk.menu.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/category/{name}")
    public String categoryView(@PathVariable String name, Model model) {
        List<MenuResponseDTO> menus = new ArrayList<>();
        switch (name) {
            case "coffee" -> menus = menuService.findMenuList("커피");
            case "smoothie" -> menus = menuService.findMenuList("스무디");
            case "ade" -> menus = menuService.findMenuList("에이드");
            case "decaf" -> menus = menuService.findMenuList("디카페인");
        }
        model.addAttribute("menus", menus);
        return "kiosk/category/" + name;
    }

    //상품 아이콘을 클릭하면 주문창으로 이동
    @PostMapping("/order")
    public String orderView(@RequestParam Long menuId, Model model) {
        MenuResponseDTO menu = menuService.getMenu(menuId);
        if(menu.isSoldOut()){
            return "kiosk/menu/soldOut";
        }
        model.addAttribute("menu", menu);
        return "kiosk/order/item";
    }

    /**
     * 관리자가 상품 리스트 관리(조회, 수정, 삭제) 및 추가하는 기능
     */

    //상품 관리 화면 이동
    @GetMapping("/manage")
    public String manageView() {
        return "kiosk/menu/manage";
    }

    //메뉴 리스트 조회
    @GetMapping("/list")
    public String menuList(Model model){
        List<MenuResponseDTO> menus = menuService.getMenus();
        model.addAttribute("menus", menus);
        return "kiosk/menu/menuList";
    }

    //메뉴 추가(화면 이동)
    @GetMapping("/add")
    public String addMenuView(Model model) {
        MenuRequestDTO menuRequest = new MenuRequestDTO();
        model.addAttribute("menu", menuRequest);
        return "kiosk/menu/addMenu";
    }

    //메뉴 추가
    @PostMapping("/add")
    public String addMenu(@Valid @ModelAttribute("menu") MenuRequestDTO menuRequestDTO,BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "kiosk/menu/addMenu";
        }
        MenuResponseDTO menuResponseDTO = menuService.saveMenu(menuRequestDTO);
        if(menuResponseDTO == null){
            bindingResult.reject("AlreadyExists","이미 존재하는 상품입니다");
            return "kiosk/menu/addMenu";
        }

        model.addAttribute("menu", menuRequestDTO);
        model.addAttribute("menuResult", menuResponseDTO);
        return "kiosk/menu/saveView";
    }

    //메뉴 삭제
    @PostMapping("/delete")
    public String deleteMenu(@RequestParam Long menuID, Model model){
        MenuResponseDTO deleteMenu = menuService.deleteMenu(menuID);
        model.addAttribute("deleteMenu", deleteMenu);
        return "kiosk/menu/deleteView";
    }

    //메뉴 수정(화면 이동)
    @GetMapping("/update")
    public String updateMenuView(@RequestParam Long menuID, Model model){
        MenuResponseDTO menu = menuService.getMenu(menuID);
        model.addAttribute("menu", menu);
        return  "kiosk/menu/updateMenu";
    }

    //메뉴 수정
    @PostMapping("/update")
    public String updateMenu(@RequestParam Long menuID, @RequestParam Integer price, @RequestParam boolean soldOut, Model model){
        MenuResponseDTO menuResponseDTO = menuService.reviseMenu(menuID, price, soldOut);
        model.addAttribute("updateMenu", menuResponseDTO);
        return "kiosk/menu/updateView";
    }

}
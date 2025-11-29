package com.example.kiosk.menu.service;

import com.example.kiosk.menu.dto.MenuRequestDTO;
import com.example.kiosk.menu.dto.MenuResponseDTO;

import java.util.List;

public interface MenuService {
    //메뉴 조회
    MenuResponseDTO getMenu(Long menuId);

    //메뉴 리스트 조회
    List<MenuResponseDTO> getMenus();

    //메뉴 등록
    MenuResponseDTO saveMenu(MenuRequestDTO menuRequestDTO);

    //메뉴 수정
    MenuResponseDTO reviseMenu(Long menuId, Integer price, boolean isSoldOut);

    //메뉴 삭제
    MenuResponseDTO deleteMenu(Long menuId);

    //카테고리에 해당하는 메뉴 전체 불러오기
    List<MenuResponseDTO> findMenuList(String category);
}
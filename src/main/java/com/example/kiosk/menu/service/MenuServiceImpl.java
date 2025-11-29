package com.example.kiosk.menu.service;

import com.example.kiosk.menu.dto.MenuRequestDTO;
import com.example.kiosk.menu.dto.MenuResponseDTO;
import com.example.kiosk.menu.entity.Menu;
import com.example.kiosk.menu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public MenuResponseDTO getMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).get();
        MenuResponseDTO menuResponseDTO = new MenuResponseDTO(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.isSoldOut(),
                menu.getCategory());
        return menuResponseDTO;
    }

    @Override
    public List<MenuResponseDTO> getMenus(){
        List<Menu> menuList = menuRepository.findAll();
        List<MenuResponseDTO> menuResponseDTOList = new ArrayList<>();
        for (Menu menu : menuList) {
            MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
            menuResponseDTO.setId(menu.getId());
            menuResponseDTO.setName(menu.getName());
            menuResponseDTO.setPrice(menu.getPrice());
            menuResponseDTO.setSoldOut(menu.isSoldOut());
            menuResponseDTO.setCategory(menu.getCategory());
            menuResponseDTOList.add(menuResponseDTO);
        }
        return menuResponseDTOList;
    }

    @Override
    public MenuResponseDTO saveMenu(MenuRequestDTO menuRequestDTO) {
        if(menuRepository.existsMenuByName(menuRequestDTO.getName())){
            return null;
        }

        Menu menu = new Menu(
                menuRequestDTO.getName(),
                menuRequestDTO.getPrice(),
                false,
                menuRequestDTO.getCategory());
        menuRepository.save(menu);
        MenuResponseDTO menuResponseDTO = new MenuResponseDTO(
                menuRepository.findByName(menuRequestDTO.getName()).getId(),
                menuRequestDTO.getName(),
                menuRequestDTO.getPrice(),
                false,
                menuRequestDTO.getCategory());
        return menuResponseDTO;
    }

    @Override
    public MenuResponseDTO reviseMenu(Long menuId, Integer price, boolean isSoldOut) {
        Menu menu = menuRepository.findById(menuId).get();
        menu.setPrice(price);
        menu.setSoldOut(isSoldOut);
        MenuResponseDTO menuResponseDTO = new MenuResponseDTO(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.isSoldOut(),
                menu.getCategory());
        menuRepository.save(menu);
        return menuResponseDTO;
    }

    @Override
    public MenuResponseDTO deleteMenu(Long menuId) {
        //이미 존재하는 리스트에서 삭제하는 것이기 때문에 예외처리를 고려하지 않아도 된다
        Menu menu = menuRepository.findById(menuId).get();
        MenuResponseDTO menuResponseDTO = new MenuResponseDTO(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.isSoldOut(),
                menu.getCategory());
        menuRepository.deleteById(menuId);
        return menuResponseDTO;
    }

    @Override
    public List<MenuResponseDTO> findMenuList(String category) {
        List<Menu> menusByCategory = menuRepository.findMenusByCategory(category);
        List<MenuResponseDTO> menuResponseDTOList = new ArrayList<>();
        for(Menu menu : menusByCategory){
            MenuResponseDTO menuResponseDTO = new MenuResponseDTO(
                    menu.getId(),
                    menu.getName(),
                    menu.getPrice(),
                    menu.isSoldOut(),
                    menu.getCategory());
            menuResponseDTOList.add(menuResponseDTO);
        }
        return menuResponseDTOList;
    }
}

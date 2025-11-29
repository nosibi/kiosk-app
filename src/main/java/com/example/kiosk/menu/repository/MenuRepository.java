package com.example.kiosk.menu.repository;

import com.example.kiosk.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByName(String name);
    boolean existsMenuByName(String name);
    List<Menu> findMenusByCategory(String category);
}

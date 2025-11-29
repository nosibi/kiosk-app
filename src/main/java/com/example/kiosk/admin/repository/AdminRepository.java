package com.example.kiosk.admin.repository;

import com.example.kiosk.admin.entity.Admin;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AdminRepository {

    private static Map<Long, Admin> adminStore = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    //관리자 등록
    public Admin registerAdmin(Admin admin){
        admin.setId(++sequence);
        adminStore.put(admin.getId(), admin);
        return admin;
    }

    //관리자 아이디로 조회
    public Optional<Admin> getAdminByLoginId(String loginId){
        return new ArrayList<>(adminStore.values()).stream().filter(admin -> admin.getLoginId().equals(loginId)).findAny();
    }

    @PostConstruct
    public void init(){
        Admin admin = new Admin();
        admin.setLoginId("admin");
        admin.setPassword("1234");
        admin.setAdminLocation("연천점");
        registerAdmin(admin);
    }
}

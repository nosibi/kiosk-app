package com.example.kiosk.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AdminRequestDTO {
    @NotEmpty(message = "지점 위치를 선택해주십시오")
    String adminLocation;
    @NotEmpty(message = "ID를 입력해주십시오")
    String loginID;
    @NotEmpty(message = "패스워드를 입력해주십시오")
    String password;
}
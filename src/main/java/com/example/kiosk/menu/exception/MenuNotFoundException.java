package com.example.kiosk.menu.exception;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException(String message) {
        super(message);
    }

    public MenuNotFoundException() {
        super("존재하지 않는 상품입니다");
    }
}

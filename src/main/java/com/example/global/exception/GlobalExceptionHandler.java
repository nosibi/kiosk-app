package com.example.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = RuntimeException.class)
    public String handleException(RuntimeException e, Model model) {
        log.info("Global ExceptionHandler triggered");
        model.addAttribute("message", e.getMessage());
        return "global/exception";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ConstraintViolationException.class)
    public Map<String,String> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, String> errors = new HashMap<>();
        errors.put("Error Type", status.getReasonPhrase());
        errors.put("URL",request.getRequestURI());
        errors.put("Error Message","유효하지 않은 입력값으로 인한 예외 발생 " + e.getMessage());

        return errors;
    }
}
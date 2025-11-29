package com.example.kiosk.admin;

import com.example.kiosk.admin.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(
                        "/order/info",
                        "/revenue/*",
                        "/menu/manage",
                        "/menu/list",
                        "/menu/add",
                        "/menu/delete",
                        "/menu/update");
    }
}
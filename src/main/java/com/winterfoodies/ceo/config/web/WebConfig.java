package com.winterfoodies.ceo.config.web;


import com.winterfoodies.ceo.domain.user.service.security.UserDetailsImpl;
import com.winterfoodies.ceo.dto.user.UserResponseDto;
import com.winterfoodies.ceo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Bean(name = "requestUser")
    @RequestScope
    public User requestUser(){
        System.out.println("HELLO");
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration regJs = registry.addResourceHandler("js/**");
        regJs.addResourceLocations("classpath:/js/");

        ResourceHandlerRegistration regCss = registry.addResourceHandler("css/**");
        regCss.addResourceLocations("classpath:/css/");

        ResourceHandlerRegistration regScss = registry.addResourceHandler("scss/**");
        regScss.addResourceLocations("classpath:/scss/");

        ResourceHandlerRegistration regVendor = registry.addResourceHandler("vendor/**");
        regVendor.addResourceLocations("classpath:/vendor/");

        ResourceHandlerRegistration regImg = registry.addResourceHandler("img/**");
        regImg.addResourceLocations("classpath:/img/");

        ResourceHandlerRegistration regStoreImg = registry.addResourceHandler("store-img/**");
        regStoreImg.addResourceLocations("file:/Users/hyunsoo/tmp/");

    }
}

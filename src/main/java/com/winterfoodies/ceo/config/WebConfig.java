package com.winterfoodies.ceo.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

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

    }
}

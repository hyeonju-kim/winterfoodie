package com.winterfoodies.ceo;

import com.winterfoodies.ceo.config.properties.ResourceProperties;
import com.winterfoodies.ceo.config.properties.UiControlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties({ResourceProperties.class, UiControlProperties.class})
@SpringBootApplication
public class CeoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CeoApplication.class, args);
    }

}

package com.winterfoodies.ceo;

import com.winterfoodies.ceo.config.ResourceProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@EnableConfigurationProperties({ResourceProperties.class})
@SpringBootApplication
public class CeoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CeoApplication.class, args);
    }

}

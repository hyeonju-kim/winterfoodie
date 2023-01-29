package com.winterfoodies.ceo.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;



@ConstructorBinding
@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "resource")
public class ResourceProperties {
    private final String rootUrl;
}

package com.winterfoodies.ceo.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "pagecontrol")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class UiControlProperties {

    private final String redirectDashboard;
    private final String redirectLogin;
    private final String redirectRegister;
}

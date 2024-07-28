package com.hs.takeover.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWSsoConfig {
    @Value("${SSO_HOST:http://localhost:8080}")
    private String ssoHost;

    @Bean
    public String getSsoHost() {
        return ssoHost;
    }
}

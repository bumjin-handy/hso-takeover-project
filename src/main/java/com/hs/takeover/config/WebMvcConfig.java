package com.hs.takeover.config;

import com.hs.takeover.interceptor.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
/*
  Java configuration file that is used for Spring MVC and Thymeleaf
  configurations
 */
public class WebMvcConfig implements WebMvcConfigurer,  ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
    }


}

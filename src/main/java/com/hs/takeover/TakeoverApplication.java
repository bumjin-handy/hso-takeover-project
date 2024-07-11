package com.hs.takeover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TakeoverApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TakeoverApplication.class, args);
    }
}

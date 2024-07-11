package com.hs.takeover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class TakeoverApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TakeoverApplication.class, args);
    }
}

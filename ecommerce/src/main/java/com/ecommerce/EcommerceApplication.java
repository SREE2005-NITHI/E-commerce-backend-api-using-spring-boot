package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
        System.out.println("========================================");
        System.out.println("  E-Commerce Backend Started!");
        System.out.println("  URL: http://localhost:8080");
        System.out.println("  By: Sreenithi - Spring Boot Project");
        System.out.println("========================================");
    }
}

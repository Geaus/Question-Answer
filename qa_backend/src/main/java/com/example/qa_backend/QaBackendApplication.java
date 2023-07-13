package com.example.qa_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class QaBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(QaBackendApplication.class, args);
    }

}

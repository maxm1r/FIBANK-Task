package com.fibanktask;

import com.fibanktask.interceptor.ApiKeyInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FibankTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(FibankTaskApplication.class, args);
    }

}

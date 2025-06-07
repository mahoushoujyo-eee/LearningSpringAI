package com.atguigu.learningspringai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class LearningSpringAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningSpringAiApplication.class, args);
    }

}

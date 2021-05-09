package com.example.scoring_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//开启事务管理
@EnableTransactionManagement
public class ScoringSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoringSystemApplication.class, args);
    }
}

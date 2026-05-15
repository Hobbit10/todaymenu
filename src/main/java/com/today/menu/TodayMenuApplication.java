package com.today.menu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.today.menu.mapper")
public class TodayMenuApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodayMenuApplication.class, args);
    }
}
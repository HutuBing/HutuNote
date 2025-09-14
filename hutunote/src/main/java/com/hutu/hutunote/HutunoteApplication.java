package com.hutu.hutunote;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan(value = {"com.hutu.hutunote.mapper"})
@EnableCaching
@EnableAsync
public class HutunoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(HutunoteApplication.class, args);
    }

}

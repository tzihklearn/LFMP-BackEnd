package com.MinNiCup.lfmpbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

@MapperScan("com.MinNiCup.lfmpbackend.mapper")
@SpringBootApplication
@CrossOrigin
@EnableTransactionManagement
@EnableScheduling
public class LfmpBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(LfmpBackEndApplication.class, args);
    }

}

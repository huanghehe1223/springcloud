package com.upc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan("com.upc.mapper")
public class QuickapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickapiApplication.class, args);
    }

}

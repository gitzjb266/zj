package com.more.transformation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.more.transformation.dao"})

@SpringBootApplication
public class TransformationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransformationApplication.class, args);
    }

}

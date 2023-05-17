package com.wumeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author wmstart
 * @create 2023-04-14 19:31
 */
@SpringBootApplication(scanBasePackages = "com.wumeng")

public class ServiceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class,args);
    }




}

package com.ibm.academia.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RestProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestProductsApplication.class, args);
    }

}

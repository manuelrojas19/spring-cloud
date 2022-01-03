package com.ibm.academia.apirest;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
//@RibbonClient(name = "rest-productos")
@EnableFeignClients
@SpringBootApplication
public class RestItemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestItemsApplication.class, args);
    }

}

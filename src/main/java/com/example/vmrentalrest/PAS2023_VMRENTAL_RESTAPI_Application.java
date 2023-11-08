package com.example.vmrentalrest;


import com.example.vmrentalrest.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class PAS2023_VMRENTAL_RESTAPI_Application {


    public static void main(String[] args) {
        SpringApplication.run(PAS2023_VMRENTAL_RESTAPI_Application.class, args);
    }

}

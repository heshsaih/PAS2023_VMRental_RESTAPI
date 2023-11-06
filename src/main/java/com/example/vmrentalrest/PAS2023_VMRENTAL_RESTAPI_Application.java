package com.example.vmrentalrest;


import com.example.vmrentalrest.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PAS2023_VMRENTAL_RESTAPI_Application {


    public static void main(String[] args) {
        UserRepository userRepository;
        SpringApplication.run(PAS2023_VMRENTAL_RESTAPI_Application.class, args);
        
        
    }

}

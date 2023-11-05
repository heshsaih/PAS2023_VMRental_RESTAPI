package com.example.nbd;


import com.example.nbd.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Nbd2023KmMwApplication {


    public static void main(String[] args) {
        UserRepository userRepository;
        SpringApplication.run(Nbd2023KmMwApplication.class, args);
        
        
    }

}

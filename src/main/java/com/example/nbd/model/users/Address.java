package com.example.nbd.model.users;

import lombok.*;
import org.springframework.data.annotation.Id;


@Data
public class Address {

    private String city;
    private String street;
    private String houseNumber;
}

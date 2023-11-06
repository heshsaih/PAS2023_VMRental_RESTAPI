package com.example.vmrentalrest.model.users;


import com.example.vmrentalrest.model.enums.ClientType;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString(callSuper = true)
@Document
public class Client extends User {

    private ClientType clientType;
    private List<String> activeRents;
}
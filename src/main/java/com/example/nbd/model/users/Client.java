package com.example.nbd.model.users;


import com.example.nbd.model.enums.ClientType;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
@Document
public class Client extends User {

    private ClientType clientType;
    private List<String> activeRents;
}
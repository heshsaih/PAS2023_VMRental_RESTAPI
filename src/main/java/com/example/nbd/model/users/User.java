package com.example.nbd.model.users;

import lombok.*;
import org.springframework.data.annotation.Id;
@Getter
@Setter
@ToString
public abstract class User {
    @Id
    private String id;
    private String username;
    private String firstName;
    private boolean isActive;
    private String lastName;
    private Address address;
}

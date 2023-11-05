package com.example.nbd.model.users;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
@Getter
@Setter
public abstract class User {
    @Id
    private String id;
    private String firstName;
    private boolean isActive;
    private String lastName;
    private Address address;
}

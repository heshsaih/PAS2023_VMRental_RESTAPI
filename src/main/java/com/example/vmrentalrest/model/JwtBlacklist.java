package com.example.vmrentalrest.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class JwtBlacklist {
    @Id
    private String id;
    private String token;
}

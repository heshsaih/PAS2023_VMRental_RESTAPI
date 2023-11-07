package com.example.vmrentalrest.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Rent {
    @Id
    private String rentId;
    private LocalDateTime startLocalDateTime;
    private LocalDateTime endLocalDateTime;
    private String userId;
    private String virtualDeviceId;


}

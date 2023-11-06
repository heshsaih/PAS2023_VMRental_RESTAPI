package com.example.vmrentalrest.model.users;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@Document
public class Administrator extends User {
}

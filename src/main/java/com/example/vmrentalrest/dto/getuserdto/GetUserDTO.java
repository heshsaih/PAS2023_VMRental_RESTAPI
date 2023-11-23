package com.example.vmrentalrest.dto.getuserdto;

import com.example.vmrentalrest.model.enums.UserType;
import com.example.vmrentalrest.model.users.*;
import lombok.*;

@Data
@NoArgsConstructor
public abstract class GetUserDTO {
    private UserType userType;
    private String id;
    private String username;
    private String firstName;
    private boolean active;
    private String email;
    private String lastName;
    private Address address;
    public GetUserDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.active = user.isActive();
        this.lastName = user.getLastName();
        this.address = user.getAddress();
        this.email = user.getEmail();
    }
}

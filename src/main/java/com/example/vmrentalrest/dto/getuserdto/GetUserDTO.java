package com.example.vmrentalrest.dto.getuserdto;

import com.example.vmrentalrest.model.enums.ClientType;
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
    private ClientType clientType;
    public GetUserDTO(User user){
        if(user instanceof Administrator){
            this.userType = UserType.ADMINISTRATOR;
        } else if(user instanceof Client){
            this.userType = UserType.CLIENT;
            this.clientType = ((Client) user).getClientType();
        } else if(user instanceof ResourceManager){
            this.userType = UserType.RESOURCE_MANAGER;
        } else {
            throw new UnknownUserTypeException();
        }
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.active = user.isActive();
        this.lastName = user.getLastName();
        this.address = user.getAddress();
        this.email = user.getEmail();
    }
}

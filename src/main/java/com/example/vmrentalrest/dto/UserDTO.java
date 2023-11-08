package com.example.vmrentalrest.dto;

import com.example.vmrentalrest.exceptions.invalidParametersExceptions.UnknownUserTypeException;
import com.example.vmrentalrest.model.enums.ClientType;
import com.example.vmrentalrest.model.enums.UserType;
import com.example.vmrentalrest.model.users.*;
import lombok.*;

import java.util.List;

@Data
public class UserDTO {
    private UserType userType;
    private String id;
    private String username;
    private String firstName;
    private boolean active;
    private String lastName;
    private Address address;
    private ClientType clientType;
    private List<String> activeRents;


    public User convertToUser(){
        if(this.getUserType() == null){
            throw new UnknownUserTypeException();
        }
        switch (this.getUserType()){
            case ADMINISTRATOR -> {
                Administrator administrator = new Administrator();
                performSettings(administrator,this);
                return administrator;
            }
            case CLIENT -> {
                Client client = new Client();
                performSettings(client,this);
                client.setClientType(this.getClientType());
                client.setActiveRents(this.getActiveRents());
                return client;
            }
            case RESOURCE_MANAGER -> {
                ResourceManager resourceManager = new ResourceManager();
                performSettings(resourceManager,this);
                return resourceManager;
            }
            default -> throw new UnknownUserTypeException();
        }

    }
    private void performSettings(User user,UserDTO userDTO) {
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setActive(userDTO.isActive());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());

    }

}

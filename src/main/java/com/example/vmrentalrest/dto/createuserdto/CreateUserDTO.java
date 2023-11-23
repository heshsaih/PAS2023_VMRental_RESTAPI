package com.example.vmrentalrest.dto.createuserdto;



import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.model.users.Address;
import com.example.vmrentalrest.model.users.User;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class CreateUserDTO {


    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Address address;

    protected void setCreateUserFromDTOProperties(User user) {
        user.setUsername(this.getUsername());
        user.setFirstName(this.getFirstName());
        user.setLastName(this.getLastName());
        user.setPassword(this.getPassword());
        user.setEmail(this.getEmail());
        user.setAddress(this.getAddress());
        user.setActive(true);
    }

}

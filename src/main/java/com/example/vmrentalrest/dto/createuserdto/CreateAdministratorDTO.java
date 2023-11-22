package com.example.vmrentalrest.dto.createuserdto;

import com.example.vmrentalrest.model.users.Administrator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CreateAdministratorDTO extends CreateUserDTO {
    public Administrator createAdministratorFromDTO() {
        Administrator administrator = new Administrator();
        setCreateUserFromDTOProperties(administrator);
        return administrator;
    }
}

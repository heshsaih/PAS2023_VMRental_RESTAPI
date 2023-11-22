package com.example.vmrentalrest.dto.createuserdto;

import com.example.vmrentalrest.model.users.Administrator;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class CreateAdminDTO extends CreateUserDTO {
    public Administrator createAdministratorFromDTO() {
        Administrator administrator = new Administrator();
        setCreateUserFromDTOProperties(administrator);
        return administrator;
    }
}

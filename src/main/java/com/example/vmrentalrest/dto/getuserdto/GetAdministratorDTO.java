package com.example.vmrentalrest.dto.getuserdto;

import com.example.vmrentalrest.model.enums.UserType;
import com.example.vmrentalrest.model.users.Administrator;

public class GetAdministratorDTO extends  GetUserDTO{
    public GetAdministratorDTO(Administrator administrator) {
        super(administrator);
        this.setUserType(UserType.ADMINISTRATOR);
    }
}

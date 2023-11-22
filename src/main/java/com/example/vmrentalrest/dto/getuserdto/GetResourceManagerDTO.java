package com.example.vmrentalrest.dto.getuserdto;

import com.example.vmrentalrest.model.enums.UserType;
import com.example.vmrentalrest.model.users.ResourceManager;
import com.example.vmrentalrest.model.users.User;

public class GetResourceManagerDTO extends GetUserDTO{
    public GetResourceManagerDTO(ResourceManager resourceManager) {
        super(resourceManager);
        this.setUserType(UserType.RESOURCE_MANAGER);
    }
}

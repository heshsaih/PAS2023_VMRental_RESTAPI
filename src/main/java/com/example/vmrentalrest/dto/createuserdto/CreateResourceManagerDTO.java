package com.example.vmrentalrest.dto.createuserdto;

import com.example.vmrentalrest.model.users.ResourceManager;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateResourceManagerDTO extends CreateUserDTO{
    public ResourceManager createResourceManagerFromDTO() {
        ResourceManager resourceManager = new ResourceManager();
        setCreateUserFromDTOProperties(resourceManager);
        return resourceManager;
    }
}

package com.example.vmrentalrest.model.users;

import com.example.vmrentalrest.dto.getuserdto.GetResourceManagerDTO;
import com.example.vmrentalrest.dto.getuserdto.GetUserDTO;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@Document
public class ResourceManager extends User{
    @Override
    public GetResourceManagerDTO convertToDTO() {
        return new GetResourceManagerDTO(this);
    }
}

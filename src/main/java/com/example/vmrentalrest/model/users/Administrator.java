package com.example.vmrentalrest.model.users;

import com.example.vmrentalrest.dto.getuserdto.GetAdministratorDTO;
import com.example.vmrentalrest.dto.getuserdto.GetUserDTO;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@Document
public class Administrator extends User {
    @Override
    public GetAdministratorDTO convertToDTO() {
        return new GetAdministratorDTO(this);
    }
}

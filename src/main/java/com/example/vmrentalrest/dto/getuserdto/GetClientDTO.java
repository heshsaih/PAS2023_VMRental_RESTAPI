package com.example.vmrentalrest.dto.getuserdto;

import com.example.vmrentalrest.model.enums.ClientType;
import com.example.vmrentalrest.model.enums.UserType;
import com.example.vmrentalrest.model.users.Client;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class GetClientDTO extends GetUserDTO {
    private ClientType clientType;

    public GetClientDTO(Client client) {
        super(client);
        this.setClientType(client.getClientType());
        this.setUserType(UserType.CLIENT);
    }
}

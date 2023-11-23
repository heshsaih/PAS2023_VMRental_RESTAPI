package com.example.vmrentalrest.dto.createuserdto;

import com.example.vmrentalrest.model.enums.ClientType;
import com.example.vmrentalrest.model.users.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateClientDTO extends CreateUserDTO {

    private ClientType clientType;

    public Client createClientFromDTO() {
        System.out.println("test1");
        Client client = new Client();
        setCreateUserFromDTOProperties(client);
        client.setClientType(this.getClientType());
        System.out.println("test2");
        return client;
    }
}

package com.example.vmrentalrest.dto.createuserdto;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.model.enums.ClientType;
import com.example.vmrentalrest.model.users.Client;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateClientDTO extends CreateUserDTO {
    @NotNull(message = ErrorMessages.BadRequestErrorMessages.CLIENT_TYPE_IS_NULL_MESSAGE)
    private ClientType clientType;

    public Client createClientFromDTO() {
        Client client = new Client();
        setCreateUserFromDTOProperties(client);
        client.setClientType(this.getClientType());
        return client;
    }
}

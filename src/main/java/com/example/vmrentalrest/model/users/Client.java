package com.example.vmrentalrest.model.users;


import com.example.vmrentalrest.dto.getuserdto.GetClientDTO;
import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.model.Role;
import com.example.vmrentalrest.model.enums.ClientType;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@ToString(callSuper = true)
@Document
public class Client extends User {

    @NotNull(message = ErrorMessages.BadRequestErrorMessages.CLIENT_TYPE_IS_NULL_MESSAGE)
    private ClientType clientType;

    @Override
    public GetClientDTO convertToDTO() {
        return new GetClientDTO(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.CLIENT.name()));
    }
}
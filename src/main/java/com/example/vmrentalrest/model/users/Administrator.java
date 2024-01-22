package com.example.vmrentalrest.model.users;

import com.example.vmrentalrest.dto.getuserdto.GetAdministratorDTO;
import com.example.vmrentalrest.model.Role;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@ToString(callSuper = true)
@Document
public class Administrator extends User {
    @Override
    public GetAdministratorDTO convertToDTO() {
        return new GetAdministratorDTO(this);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.ADMINISTRATOR.name()));
    }


}

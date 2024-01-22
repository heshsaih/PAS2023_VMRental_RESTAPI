package com.example.vmrentalrest.model.users;

import com.example.vmrentalrest.dto.getuserdto.GetUserDTO;
import com.example.vmrentalrest.exceptions.ErrorMessages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
public abstract class User implements  UserDetails{
    private String id;


    @Size(min = 3, max = 30, message = ErrorMessages.BadRequestErrorMessages.USERNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS_MESSAGE)
    private String username;

    @Size(min = 3, max = 30, message = ErrorMessages.BadRequestErrorMessages.FIRSTNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS_MESSAGE)
    private String firstName;

    @Size(min = 3, max = 30, message = ErrorMessages.BadRequestErrorMessages.LASTNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS_MESSAGE)
    private String lastName;


    @Size(min = 3, max = 64, message = ErrorMessages.BadRequestErrorMessages.PASSWORD_MUST_BE_BETWEEN_3_AND_64_CHARACTERS_MESSAGE)
    private String password;
    @Email(message = ErrorMessages.BadRequestErrorMessages.INVALID_EMAIL_MESSAGE)
    private String email;

    private boolean active;

    @NotNull(message = ErrorMessages.BadRequestErrorMessages.ADDRESS_IS_NULL_MESSAGE)
    @Valid
    private Address address;

    public abstract GetUserDTO convertToDTO();

    @Override
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}

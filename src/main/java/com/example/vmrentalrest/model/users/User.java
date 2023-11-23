package com.example.vmrentalrest.model.users;

import com.example.vmrentalrest.dto.getuserdto.GetUserDTO;
import com.example.vmrentalrest.exceptions.ErrorMessages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
public abstract class User {
    @Id
    private String id;


    @Size(min = 3, max = 30, message = ErrorMessages.BadRequestErrorMessages.USERNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS_MESSAGE)
    private String username;

    @Size(min = 3, max = 30, message = ErrorMessages.BadRequestErrorMessages.FIRSTNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS_MESSAGE)
    private String firstName;

    @Size(min = 3, max = 30, message = ErrorMessages.BadRequestErrorMessages.LASTNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS_MESSAGE)
    private String lastName;


    @Size(min = 3, max = 30, message = ErrorMessages.BadRequestErrorMessages.PASSWORD_MUST_BE_BETWEEN_3_AND_30_CHARACTERS_MESSAGE)
    private String password;
    @Email(message = ErrorMessages.BadRequestErrorMessages.INVALID_EMAIL_MESSAGE)
    private String email;

    private boolean active;

    @NotNull(message = ErrorMessages.BadRequestErrorMessages.ADDRESS_IS_NULL_MESSAGE)
    @Valid
    private Address address;

    public abstract GetUserDTO convertToDTO();
}

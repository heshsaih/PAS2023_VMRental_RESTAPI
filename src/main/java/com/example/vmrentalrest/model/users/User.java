package com.example.vmrentalrest.model.users;

import com.example.vmrentalrest.dto.getuserdto.GetUserDTO;
import com.example.vmrentalrest.exceptions.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
@Validated
public abstract class User {
    @Id
    private String id;

    @Min(value = 3, message = ErrorMessages.BadRequestErrorMessages.USERNAME_IS_TOO_SHORT_MESSAGE)
    @Max(value = 30, message = ErrorMessages.BadRequestErrorMessages.USERNAME_IS_TOO_LONG_MESSAGE)
    private String username;

    @Min(value = 2, message = ErrorMessages.BadRequestErrorMessages.FIRSTNAME_IS_TOO_SHORT_MESSAGE)
    @Max(value = 30, message = ErrorMessages.BadRequestErrorMessages.FIRSTNAME_IS_TOO_LONG_MESSAGE)
    private String firstName;

    @Min(value = 2, message = ErrorMessages.BadRequestErrorMessages.LASTNAME_IS_TOO_SHORT_MESSAGE)
    @Max(value = 40, message = ErrorMessages.BadRequestErrorMessages.LASTNAME_IS_TOO_LONG_MESSAGE)
    private String lastName;
    @NotNull(message = ErrorMessages.BadRequestErrorMessages.PASSWORD_IS_NULL_MESSAGE)
    @Min(value = 3, message = ErrorMessages.BadRequestErrorMessages.PASSWORD_IS_TOO_SHORT_MESSAGE)
    @Max(value = 20, message = ErrorMessages.BadRequestErrorMessages.PASSWORD_IS_TOO_LONG_MESSAGE)
    private String password;
    @Email(message = ErrorMessages.BadRequestErrorMessages.INVALID_EMAIL_MESSAGE)
    private String email;
    private boolean active;
    @NotNull(message = ErrorMessages.BadRequestErrorMessages.ADDRESS_IS_NULL_MESSAGE)
    private Address address;
    public abstract GetUserDTO convertToDTO();
}

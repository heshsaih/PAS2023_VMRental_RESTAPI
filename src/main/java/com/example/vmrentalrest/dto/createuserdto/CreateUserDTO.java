package com.example.vmrentalrest.dto.createuserdto;



import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.model.users.Address;
import com.example.vmrentalrest.model.users.User;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class CreateUserDTO {


    @Min(value = 3, message = ErrorMessages.BadRequestErrorMessages.USERNAME_IS_TOO_SHORT_MESSAGE)
    @Max(value = 30, message = ErrorMessages.BadRequestErrorMessages.USERNAME_IS_TOO_LONG_MESSAGE)
    private String username;

    @Min(value = 2, message = ErrorMessages.BadRequestErrorMessages.FIRSTNAME_IS_TOO_SHORT_MESSAGE)
    @Max(value = 30, message = ErrorMessages.BadRequestErrorMessages.FIRSTNAME_IS_TOO_LONG_MESSAGE)
    private String firstName;

    @Min(value = 2, message = ErrorMessages.BadRequestErrorMessages.LASTNAME_IS_TOO_SHORT_MESSAGE)
    @Max(value = 40, message = ErrorMessages.BadRequestErrorMessages.LASTNAME_IS_TOO_LONG_MESSAGE)
    private String lastName;

    @Min(value = 3, message = ErrorMessages.BadRequestErrorMessages.PASSWORD_IS_TOO_SHORT_MESSAGE)
    @Max(value = 20, message = ErrorMessages.BadRequestErrorMessages.PASSWORD_IS_TOO_LONG_MESSAGE)
    private String password;

    @Email(message = ErrorMessages.BadRequestErrorMessages.INVALID_EMAIL_MESSAGE)
    private String email;
    @NotNull(message = ErrorMessages.BadRequestErrorMessages.ADDRESS_IS_NULL_MESSAGE)
    private Address address;

    protected void setCreateUserFromDTOProperties(User user) {
        user.setUsername(this.getUsername());
        user.setFirstName(this.getFirstName());
        user.setLastName(this.getLastName());
        user.setPassword(this.getPassword());
        user.setEmail(this.getEmail());
        user.setAddress(this.getAddress());
        user.setActive(true);
    }

}

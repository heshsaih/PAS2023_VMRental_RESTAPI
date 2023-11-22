package com.example.vmrentalrest.dto.updatedto;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.model.users.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateUserDTO(String firstName,
                            String lastName,
                            String password,
                            String email,
                            Address address
) {

}

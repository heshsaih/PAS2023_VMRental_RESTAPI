package com.example.vmrentalrest.model.users;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;


@Data
public class Address {
    @Min(value = 2, message = ErrorMessages.BadRequestErrorMessages.CITY_IS_TOO_SHORT_MESSAGE)
    @Max(value = 30, message = ErrorMessages.BadRequestErrorMessages.CITY_IS_TOO_LONG_MESSAGE)
    private String city;
    @Min(value = 2, message = ErrorMessages.BadRequestErrorMessages.STREET_IS_TOO_SHORT_MESSAGE)
    @Max(value = 30, message = ErrorMessages.BadRequestErrorMessages.STREET_IS_TOO_LONG_MESSAGE)
    private String street;
    @Min(value = 1, message = ErrorMessages.BadRequestErrorMessages.HOUSE_NUMBER_IS_TOO_SHORT_MESSAGE)
    @Max(value = 10, message = ErrorMessages.BadRequestErrorMessages.HOUSE_NUMBER_IS_TOO_LONG_MESSAGE)
    private String houseNumber;
}

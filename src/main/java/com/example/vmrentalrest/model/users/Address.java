package com.example.vmrentalrest.model.users;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
public class Address {

    @Size(min = 2, max = 30, message = ErrorMessages.BadRequestErrorMessages.CITY_MUST_BE_BETWEEN_2_AND_30_CHARACTERS_MESSAGE)
    private String city;

    @Size(min = 2, max = 30, message = ErrorMessages.BadRequestErrorMessages.STREET_MUST_BE_BETWEEN_2_AND_30_CHARACTERS_MESSAGE)
    private String street;

    @Size(min = 1, max = 10, message = ErrorMessages.BadRequestErrorMessages.HOUSE_NUMBER_MUST_BE_BETWEEN_1_AND_10_CHARACTERS_MESSAGE)
    private String houseNumber;
}

package com.example.vmrentalrest.model.virtualdevices;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@Document
public class VirtualPhone extends VirtualDevice {

    @Min(value = 100000000, message = ErrorMessages.BadRequestErrorMessages.PHONE_NUMBER_MUST_HAVE_9_DIGITS_MESSAGE)
    @Max(value = 999999999, message = ErrorMessages.BadRequestErrorMessages.PHONE_NUMBER_MUST_HAVE_9_DIGITS_MESSAGE)
    private int phoneNumber;


}

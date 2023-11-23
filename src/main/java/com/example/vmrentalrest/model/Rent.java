package com.example.vmrentalrest.model;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Rent {

    @Id
    private String rentId;
    @NotNull(message = ErrorMessages.BadRequestErrorMessages.START_DATE_IS_NULL_MESSAGE)
    private LocalDateTime startLocalDateTime;
    @NotNull(message = ErrorMessages.BadRequestErrorMessages.END_DATE_IS_NULL_MESSAGE)
    private LocalDateTime endLocalDateTime;
    @NotNull(message = ErrorMessages.BadRequestErrorMessages.CLIENT_ID_IS_NULL_MESSAGE)
    private String userId;
    @NotNull(message = ErrorMessages.BadRequestErrorMessages.VIRTUAL_DEVICE_ID_IS_NULL_MESSAGE)
    private String virtualDeviceId;


}

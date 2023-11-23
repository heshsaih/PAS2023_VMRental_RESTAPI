package com.example.vmrentalrest.model.virtualdevices;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.model.enums.OperatingSystemType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@Document
public class VirtualMachine extends VirtualDevice {
    @NotNull(message = ErrorMessages.BadRequestErrorMessages.OPERATING_SYSTEM_TYPE_IS_NULL_MESSAGE)
    private OperatingSystemType operatingSystemType;

}

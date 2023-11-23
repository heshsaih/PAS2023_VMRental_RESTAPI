package com.example.vmrentalrest.model.virtualdevices;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
public abstract class VirtualDevice {
    @Id
    private String id;
    @Min(value = 32, message = ErrorMessages.BadRequestErrorMessages.STORAGE_SIZE_MUST_BE_GREATER_OR_EQUAL_32_MESSAGE)
    @Max(value = 1024, message = ErrorMessages.BadRequestErrorMessages.STORAGE_SIZE_MUST_BE_LESS_OR_EQUAL_1024_MESSAGE)
    private int storageSize;
    @Min(value = 1, message = ErrorMessages.BadRequestErrorMessages.CPU_CORES_MUST_BE_GREATER_OR_EQUAL_1_MESSAGE)
    @Max(value = 64, message = ErrorMessages.BadRequestErrorMessages.CPU_CORES_MUST_BE_LESS_OR_EQUAL_64_MESSAGE)
    private int cpuCores;
    @Min(value = 4, message = ErrorMessages.BadRequestErrorMessages.RAM_MUST_BE_GREATER_OR_EQUAL_4_MESSAGE)
    @Max(value = 1024, message = ErrorMessages.BadRequestErrorMessages.RAM_MUST_BE_LESS_OR_EQUAL_1024_MESSAGE)
    private int ram;
}

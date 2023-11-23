package com.example.vmrentalrest.dto.updatedto;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.annotation.Id;

public record UpdateVirtualDeviceDTO(
        int storageSize,
        int cpuCores,
        int ram
) {}

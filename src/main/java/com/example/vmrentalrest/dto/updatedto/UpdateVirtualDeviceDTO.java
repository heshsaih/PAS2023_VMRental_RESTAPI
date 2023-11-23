package com.example.vmrentalrest.dto.updatedto;


public record UpdateVirtualDeviceDTO(
        int storageSize,
        int cpuCores,
        int ram
) {}

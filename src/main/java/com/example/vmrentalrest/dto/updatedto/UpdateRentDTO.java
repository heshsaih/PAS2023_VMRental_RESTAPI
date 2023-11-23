package com.example.vmrentalrest.dto.updatedto;

import java.time.LocalDateTime;

public record UpdateRentDTO(
        LocalDateTime startDate,
        LocalDateTime endDate
) {}

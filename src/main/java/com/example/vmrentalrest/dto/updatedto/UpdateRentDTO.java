package com.example.vmrentalrest.dto.updatedto;

import java.time.LocalDateTime;

public record UpdateRentDTO(
        LocalDateTime startLocalDateTime,
        LocalDateTime endLocalDateTime
) {}

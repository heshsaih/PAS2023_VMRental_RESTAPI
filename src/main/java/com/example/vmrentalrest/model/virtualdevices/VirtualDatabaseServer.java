package com.example.vmrentalrest.model.virtualdevices;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.model.enums.DatabaseType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@Document
public class VirtualDatabaseServer extends VirtualDevice {
    @NotNull(message = ErrorMessages.BadRequestErrorMessages.DATABASE_TYPE_IS_NULL_MESSAGE)
    private DatabaseType databaseType;

}

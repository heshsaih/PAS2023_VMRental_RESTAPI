package com.example.vmrentalrest.model.virtualdevices;

import com.example.vmrentalrest.model.enums.DatabaseType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@Document
public class VirtualDatabaseServer extends VirtualDevice {
    private DatabaseType databaseType;
}

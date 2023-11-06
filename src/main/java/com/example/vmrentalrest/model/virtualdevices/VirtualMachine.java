package com.example.vmrentalrest.model.virtualdevices;

import com.example.vmrentalrest.model.enums.OperatingSystemType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@Document
public class VirtualMachine extends VirtualDevice {
    private OperatingSystemType operatingSystemType;
}

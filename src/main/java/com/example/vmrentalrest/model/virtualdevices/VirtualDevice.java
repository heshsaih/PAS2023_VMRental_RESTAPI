package com.example.vmrentalrest.model.virtualdevices;

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
    private int storageSize;
    private int cpuCores;
    private int ram;
}

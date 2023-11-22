package com.example.vmrentalrest.dto;

import com.example.vmrentalrest.model.enums.DatabaseType;
import com.example.vmrentalrest.model.enums.OperatingSystemType;
import com.example.vmrentalrest.model.enums.VirtualDeviceType;
import com.example.vmrentalrest.model.virtualdevices.VirtualDatabaseServer;
import com.example.vmrentalrest.model.virtualdevices.VirtualDevice;
import com.example.vmrentalrest.model.virtualdevices.VirtualMachine;
import com.example.vmrentalrest.model.virtualdevices.VirtualPhone;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VirtualDeviceDTO {
    private VirtualDeviceType virtualDeviceType;
    private String id;
    private int storageSize;
    private int cpuCores;
    private int ram;
    private OperatingSystemType operatingSystemType;
    private DatabaseType databaseType;
    private int phoneNumber;

    public VirtualDeviceDTO(VirtualDevice virtualDevice) {
        if(virtualDevice instanceof VirtualDatabaseServer) {
            this.virtualDeviceType = VirtualDeviceType.VIRTUAL_DATABASE_SERVER;
            this.databaseType = ((VirtualDatabaseServer) virtualDevice).getDatabaseType();
        } else if(virtualDevice instanceof VirtualMachine) {
            this.virtualDeviceType = VirtualDeviceType.VIRTUAL_MACHINE;
            this.operatingSystemType = ((VirtualMachine) virtualDevice).getOperatingSystemType();
        } else if(virtualDevice instanceof VirtualPhone) {
            this.virtualDeviceType = VirtualDeviceType.VIRTUAL_PHONE;
            this.phoneNumber = ((VirtualPhone) virtualDevice).getPhoneNumber();
        } else {
            throw new UnknownVirtualDeviceTypeException();
        }
        this.storageSize = virtualDevice.getStorageSize();
        this.cpuCores = virtualDevice.getCpuCores();
        this.ram =  virtualDevice.getRam();
        this.id = virtualDevice.getId();
    }

    public VirtualDevice convertToVirtualDevice(){
        if(this.virtualDeviceType == null) {
            throw new UnknownVirtualDeviceTypeException();
        }
        switch (this.virtualDeviceType) {
            case VIRTUAL_DATABASE_SERVER -> {
                VirtualDatabaseServer virtualDatabaseServer = new VirtualDatabaseServer();
                virtualDatabaseServer.setStorageSize(this.storageSize);
                virtualDatabaseServer.setCpuCores(this.cpuCores);
                virtualDatabaseServer.setRam(this.ram);
                virtualDatabaseServer.setDatabaseType(this.databaseType);
                return virtualDatabaseServer;
            }
            case VIRTUAL_MACHINE -> {
                VirtualMachine virtualMachine = new VirtualMachine();
                virtualMachine.setStorageSize(this.storageSize);
                virtualMachine.setCpuCores(this.cpuCores);
                virtualMachine.setRam(this.ram);
                virtualMachine.setOperatingSystemType(this.operatingSystemType);
                return virtualMachine;
            }
            case VIRTUAL_PHONE -> {
                VirtualPhone virtualPhone = new VirtualPhone();
                virtualPhone.setStorageSize(this.storageSize);
                virtualPhone.setCpuCores(this.cpuCores);
                virtualPhone.setRam(this.ram);
                virtualPhone.setPhoneNumber(this.phoneNumber);
                return virtualPhone;
            }
            default -> throw new UnknownVirtualDeviceTypeException();
        }

    }
}

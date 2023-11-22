package com.example.vmrentalrest.managers;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.exceptions.illegalOperationExceptions.DeviceHasAllocationException;
import com.example.vmrentalrest.exceptions.illegalOperationExceptions.IllegalOperationException;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.VirtualDeviceNotFoundException;
import com.example.vmrentalrest.model.enums.VirtualDeviceType;
import com.example.vmrentalrest.model.virtualdevices.VirtualDatabaseServer;
import com.example.vmrentalrest.model.virtualdevices.VirtualDevice;
import com.example.vmrentalrest.model.virtualdevices.VirtualMachine;
import com.example.vmrentalrest.model.virtualdevices.VirtualPhone;
import com.example.vmrentalrest.repositories.RentRepository;
import com.example.vmrentalrest.repositories.VirtualDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RequiredArgsConstructor
public class VirtualDeviceManager {

    private final VirtualDeviceRepository virtualDeviceRepository;
    private final RentRepository rentRepository;
    public VirtualDevice createVirtualDevice(VirtualDevice virtualDevice, VirtualDeviceType virtualDeviceType) throws IllegalOperationException {
        if(virtualDevice == null
            || virtualDeviceType == null
            || !isVirtualDeviceValid(virtualDevice,virtualDeviceType)) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USER_TYPE_NOT_SUPPORTED_MESSAGE);
        }
        switch(virtualDeviceType) {
            case VIRTUAL_DATABASE_SERVER -> {
                VirtualDatabaseServer virtualDatabaseServer = new VirtualDatabaseServer();
                setVirtualDeviceProperties(virtualDatabaseServer,virtualDevice);
                virtualDatabaseServer.setDatabaseType(((VirtualDatabaseServer) virtualDevice).getDatabaseType());
                virtualDeviceRepository.save(virtualDatabaseServer);
                return virtualDatabaseServer;
            }
            case VIRTUAL_MACHINE -> {
                VirtualMachine virtualMachine = new VirtualMachine();
                setVirtualDeviceProperties(virtualMachine,virtualDevice);
                virtualMachine.setOperatingSystemType(((VirtualMachine) virtualDevice).getOperatingSystemType());
                virtualDeviceRepository.save(virtualMachine);
                return virtualMachine;
            }
            case VIRTUAL_PHONE -> {
                VirtualPhone virtualPhone = new VirtualPhone();
                setVirtualDeviceProperties(virtualPhone,virtualDevice);
                virtualPhone.setPhoneNumber(((VirtualPhone) virtualDevice).getPhoneNumber());
                virtualDeviceRepository.save(virtualPhone);
                return virtualPhone;
            }
            default -> throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USER_TYPE_NOT_SUPPORTED_MESSAGE);
        }
    }

    private boolean isVirtualDeviceValid(VirtualDevice virtualDevice, VirtualDeviceType virtualDeviceType) {
        try{
            if(virtualDevice.getCpuCores() <= 0 || virtualDevice.getRam() <= 0 || virtualDevice.getStorageSize() <= 0) {
                return false;
            }
            switch(virtualDeviceType) {
                case VIRTUAL_DATABASE_SERVER -> {
                    if(((VirtualDatabaseServer) virtualDevice).getDatabaseType() == null) {
                        return false;
                    }
                }
                case VIRTUAL_MACHINE -> {
                    if(((VirtualMachine) virtualDevice).getOperatingSystemType() == null) {
                        return false;
                    }
                }
                case VIRTUAL_PHONE -> {
                    if(((VirtualPhone) virtualDevice).getPhoneNumber() < 100000000) {
                        return false;
                    }
                }
                default -> throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USER_TYPE_NOT_SUPPORTED_MESSAGE);
            }
        } catch (IllegalOperationException e) {
            return false;
        }
        return true;
    }

    private void setVirtualDeviceProperties(VirtualDevice nonAbstractVirtualDevice, VirtualDevice virtualDevice) throws IllegalOperationException{
        try {
            nonAbstractVirtualDevice.setCpuCores(virtualDevice.getCpuCores());
            nonAbstractVirtualDevice.setRam(virtualDevice.getRam());
            nonAbstractVirtualDevice.setStorageSize(virtualDevice.getStorageSize());
        } catch (NullPointerException e) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USER_TYPE_NOT_SUPPORTED_MESSAGE);
        }
    }
    public VirtualDevice updateVirtualDevice(String id,VirtualDevice virtualDevice) throws VirtualDeviceNotFoundException {
        var value = virtualDeviceRepository.findById(id).orElseThrow(VirtualDeviceNotFoundException::new);
        if(virtualDevice.getCpuCores() > 0) value.setCpuCores(virtualDevice.getCpuCores());
        if(virtualDevice.getRam() > 0) value.setRam(virtualDevice.getRam());
        if(virtualDevice.getStorageSize() > 0) value.setStorageSize(virtualDevice.getStorageSize());
        if(value instanceof VirtualDatabaseServer
                && ((VirtualDatabaseServer) virtualDevice).getDatabaseType() != null
                && !((VirtualDatabaseServer) virtualDevice).getDatabaseType().equals(((VirtualDatabaseServer) value).getDatabaseType())) {
            ((VirtualDatabaseServer) value).setDatabaseType(((VirtualDatabaseServer) virtualDevice).getDatabaseType());
        } else if(value instanceof VirtualMachine
                && ((VirtualMachine) virtualDevice).getOperatingSystemType() != null
                && !((VirtualMachine) virtualDevice).getOperatingSystemType().equals(((VirtualMachine) value).getOperatingSystemType())) {
            ((VirtualMachine) value).setOperatingSystemType(((VirtualMachine) virtualDevice).getOperatingSystemType());
        } else if(value instanceof VirtualPhone
                && ((VirtualPhone) virtualDevice).getPhoneNumber() > 100000000
                && ((VirtualPhone) virtualDevice).getPhoneNumber() != (((VirtualPhone) value).getPhoneNumber())) {
            ((VirtualPhone) value).setPhoneNumber(((VirtualPhone) virtualDevice).getPhoneNumber());
        }
        virtualDeviceRepository.save(value);
        return value;
    }
    public void deleteVirtualDevice(String id) throws DeviceHasAllocationException, VirtualDeviceNotFoundException {
        if(rentRepository.findAll().stream().anyMatch(rent -> rent.getVirtualDeviceId().equals(id))) {
            throw new DeviceHasAllocationException();
        }
        virtualDeviceRepository.findById(id).orElseThrow(VirtualDeviceNotFoundException::new);
        virtualDeviceRepository.deleteById(id);

    }


    public List<VirtualDevice> findAllVirtualDevices() {
        return virtualDeviceRepository.findAll();
    }
    public VirtualDevice findVirtualDeviceById(String id) throws VirtualDeviceNotFoundException {
        return virtualDeviceRepository.findById(id).orElseThrow(VirtualDeviceNotFoundException::new);
    }

}

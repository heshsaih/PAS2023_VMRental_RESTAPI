package com.example.nbd.managers;

import com.example.nbd.exceptions.DeviceHasAllocationException;
import com.example.nbd.exceptions.invalidParametersExceptions.InvalidVirtualDeviceException;
import com.example.nbd.exceptions.recordNotFoundExceptions.VirtualDeviceNotFoundException;
import com.example.nbd.model.enums.VirtualDeviceType;
import com.example.nbd.model.virtualdevices.VirtualDatabaseServer;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.model.virtualdevices.VirtualMachine;
import com.example.nbd.model.virtualdevices.VirtualPhone;
import com.example.nbd.repositories.RentRepository;
import com.example.nbd.repositories.VirtualDeviceRepository;
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
    public VirtualDevice createVirtualDevice(VirtualDevice virtualDevice, VirtualDeviceType virtualDeviceType) throws InvalidVirtualDeviceException {
        if(virtualDevice == null
            || virtualDeviceType == null
            || !isVirtualDeviceValid(virtualDevice,virtualDeviceType)) {
            throw new InvalidVirtualDeviceException();
        }
        switch(virtualDeviceType) {
            case VIRTUAL_DATABASE_SERVER -> {
                VirtualDatabaseServer virtualDatabaseServer = new VirtualDatabaseServer();
                setVirtualDeviceProperties(virtualDatabaseServer,virtualDevice);
                virtualDatabaseServer.setDatabase(((VirtualDatabaseServer) virtualDevice).getDatabase());
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
            default -> throw new InvalidVirtualDeviceException();
        }
    }

    private boolean isVirtualDeviceValid(VirtualDevice virtualDevice, VirtualDeviceType virtualDeviceType) {
        try{
            if(virtualDevice.getCpuCores() <= 0 || virtualDevice.getRam() <= 0 || virtualDevice.getStorageSize() <= 0) {
                return false;
            }
            switch(virtualDeviceType) {
                case VIRTUAL_DATABASE_SERVER -> {
                    if(((VirtualDatabaseServer) virtualDevice).getDatabase() == null) {
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
                default -> throw new InvalidVirtualDeviceException();
            }
        } catch (InvalidVirtualDeviceException e) {
            return false;
        }
        return true;
    }

    private void setVirtualDeviceProperties(VirtualDevice nonAbstractVirtualDevice, VirtualDevice virtualDevice) throws InvalidVirtualDeviceException {
        try {
            nonAbstractVirtualDevice.setCpuCores(virtualDevice.getCpuCores());
            nonAbstractVirtualDevice.setRam(virtualDevice.getRam());
            nonAbstractVirtualDevice.setStorageSize(virtualDevice.getStorageSize());
        } catch (NullPointerException e) {
            throw new InvalidVirtualDeviceException();
        }
    }
    public VirtualDevice updateVirtualDevice(String id,VirtualDevice virtualDevice) throws VirtualDeviceNotFoundException {
        var virtualDeviceOpt = virtualDeviceRepository.findById(id);
        virtualDeviceOpt.ifPresent(value ->{
            if(virtualDevice.getCpuCores() > 0) value.setCpuCores(virtualDevice.getCpuCores());
            if(virtualDevice.getRam() > 0) value.setRam(virtualDevice.getRam());
            if(virtualDevice.getStorageSize() > 0) value.setStorageSize(virtualDevice.getStorageSize());
            if(value instanceof VirtualDatabaseServer
                    && ((VirtualDatabaseServer) virtualDevice).getDatabase() != null
                    && !((VirtualDatabaseServer) virtualDevice).getDatabase().equals(((VirtualDatabaseServer) value).getDatabase())) {
                ((VirtualDatabaseServer) value).setDatabase(((VirtualDatabaseServer) virtualDevice).getDatabase());
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
        });
        return virtualDeviceOpt.orElseThrow(VirtualDeviceNotFoundException::new);
    }
    public void deleteVirtualDevice(String id) throws DeviceHasAllocationException, VirtualDeviceNotFoundException {
        if(rentRepository.findAll().stream().anyMatch(rent -> rent.getVirtualDeviceId().equals(id))) {
            virtualDeviceRepository.findById(id).orElseThrow(DeviceHasAllocationException::new);
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

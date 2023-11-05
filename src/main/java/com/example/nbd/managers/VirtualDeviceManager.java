package com.example.nbd.managers;

import com.example.nbd.exceptions.NoMatchingDeviceFoundException;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;
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
    public VirtualDevice createVirtualDevice(VirtualDevice virtualDevice, VirtualDeviceType virtualDeviceType) throws NoMatchingDeviceFoundException {
        if(virtualDeviceType == null) {
            throw new NoMatchingDeviceFoundException();
        }
        switch(virtualDeviceType) {
            case VIRTUAL_DATABASE_SERVER -> {
                VirtualDatabaseServer virtualDatabaseServer = new VirtualDatabaseServer();
                setVirtualDeviceProperties(virtualDatabaseServer,virtualDevice);
                virtualDatabaseServer.setDatabase(((VirtualDatabaseServer) virtualDevice).getDatabase());
                virtualDeviceRepository.save(virtualDatabaseServer);
            }
            case VIRTUAL_MACHINE -> {
                VirtualMachine virtualMachine = new VirtualMachine();
                setVirtualDeviceProperties(virtualMachine,virtualDevice);
                virtualMachine.setOperatingSystemType(((VirtualMachine) virtualDevice).getOperatingSystemType());
                virtualDeviceRepository.save(virtualMachine);
            }
            case VIRTUAL_PHONE -> {
                VirtualPhone virtualPhone = new VirtualPhone();
                setVirtualDeviceProperties(virtualPhone,virtualDevice);
                virtualPhone.setPhoneNumber(((VirtualPhone) virtualDevice).getPhoneNumber());
                virtualDeviceRepository.save(virtualPhone);
            }

        }
        throw new NoMatchingDeviceFoundException();
    }

    private void setVirtualDeviceProperties(VirtualDevice nonAbstractVirtualDevice, VirtualDevice virtualDevice){
        nonAbstractVirtualDevice.setCpuCores(virtualDevice.getCpuCores());
        nonAbstractVirtualDevice.setRam(virtualDevice.getRam());
        nonAbstractVirtualDevice.setStorageSize(virtualDevice.getStorageSize());
    }
    public void updateVirtualDevice(String id,VirtualDevice virtualDevice) {
        var virtualMachineOpt = virtualDeviceRepository.findById(id);
        if(virtualMachineOpt.isPresent()) {
            VirtualDevice virtualDevice1 = virtualMachineOpt.get();
            virtualDevice1.setCpuCores(virtualDevice.getCpuCores());
            virtualDevice1.setRam(virtualDevice.getRam());
            virtualDevice1.setStorageSize(virtualDevice.getStorageSize());
            if(virtualDevice1 instanceof VirtualDatabaseServer) {
                ((VirtualDatabaseServer) virtualDevice1).setDatabase(((VirtualDatabaseServer) virtualDevice).getDatabase());
            } else if(virtualDevice1 instanceof VirtualMachine) {
                ((VirtualMachine) virtualDevice1).setOperatingSystemType(((VirtualMachine) virtualDevice).getOperatingSystemType());
            } else if(virtualDevice1 instanceof VirtualPhone) {
                ((VirtualPhone) virtualDevice1).setPhoneNumber(((VirtualPhone) virtualDevice).getPhoneNumber());
            }
            virtualDeviceRepository.save(virtualDevice1);
        }
    }
    public VirtualDevice deleteVirtualDevice(String id) {
        if(rentRepository.findAll().stream().anyMatch(rent -> rent.getVirtualDeviceId().equals(id))) {
            return virtualDeviceRepository.findById(id).orElse(null);
        }
        virtualDeviceRepository.deleteById(id);
        return virtualDeviceRepository.findById(id).orElse(null);
    }

    public List<VirtualDevice> findAllVirtualDevices() {
        return virtualDeviceRepository.findAll();
    }
    public VirtualDevice getVirtualDeviceById(String id) {
        return virtualDeviceRepository.findById(id).orElse(null);
    }

}

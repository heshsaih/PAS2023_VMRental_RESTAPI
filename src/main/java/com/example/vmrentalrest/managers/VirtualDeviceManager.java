package com.example.vmrentalrest.managers;

import com.example.vmrentalrest.CustomValidator;
import com.example.vmrentalrest.dto.updatedto.UpdateVirtualDeviceDTO;
import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.exceptions.RecordNotFoundException;
import com.example.vmrentalrest.exceptions.IllegalOperationException;
import com.example.vmrentalrest.model.virtualdevices.VirtualDatabaseServer;
import com.example.vmrentalrest.model.virtualdevices.VirtualDevice;
import com.example.vmrentalrest.model.virtualdevices.VirtualMachine;
import com.example.vmrentalrest.model.virtualdevices.VirtualPhone;
import com.example.vmrentalrest.repositories.RentRepository;
import com.example.vmrentalrest.repositories.VirtualDeviceRepository;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RequiredArgsConstructor
public class VirtualDeviceManager {

    private final CustomValidator customValidator;
    private final VirtualDeviceRepository virtualDeviceRepository;
    private final RentRepository rentRepository;

    public VirtualMachine createVirtualMachine(VirtualMachine virtualMachine) {
        customValidator.validate(virtualMachine);
        virtualDeviceRepository.save(virtualMachine);
        return virtualMachine;
    }
    public VirtualDatabaseServer createVirtualDatabaseServer(VirtualDatabaseServer virtualDatabaseServer) {
        customValidator.validate(virtualDatabaseServer);
        virtualDeviceRepository.save(virtualDatabaseServer);
        return virtualDatabaseServer;
    }
    public VirtualPhone createVirtualPhone(VirtualPhone virtualPhone) {
        customValidator.validate(virtualPhone);
        virtualDeviceRepository.save(virtualPhone);
        return virtualPhone;
    }




    public VirtualDevice updateVirtualDevice(String id, UpdateVirtualDeviceDTO updateVirtualDeviceDTO) {
        var value = virtualDeviceRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.VIRTUAL_DEVICE_NOT_FOUND_MESSAGE));
        if(updateVirtualDeviceDTO == null) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.BODY_IS_NULL_MESSAGE);
        }
        if(updateVirtualDeviceDTO.cpuCores() > 0) {
            value.setCpuCores(updateVirtualDeviceDTO.cpuCores());
        }
        if(updateVirtualDeviceDTO.ram() > 0) {
            value.setRam(updateVirtualDeviceDTO.ram());
        }
        if(updateVirtualDeviceDTO.storageSize() > 0) {
            value.setStorageSize(updateVirtualDeviceDTO.storageSize());
        }
        customValidator.validate(value);
        virtualDeviceRepository.save(value);
        return value;
    }
    public void deleteVirtualDevice(String id) {
        if(rentRepository.findAll().stream().anyMatch(rent -> rent.getVirtualDeviceId().equals(id))) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.DEVICE_ALREADY_RENTED_MESSAGE);
        }
        virtualDeviceRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.VIRTUAL_DEVICE_NOT_FOUND_MESSAGE));
        virtualDeviceRepository.deleteById(id);

    }


    public List<VirtualDevice> findAllVirtualDevices() {
        return virtualDeviceRepository.findAll();
    }
    public VirtualDevice findVirtualDeviceById(String id) {
        return virtualDeviceRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.VIRTUAL_DEVICE_NOT_FOUND_MESSAGE));
    }

}

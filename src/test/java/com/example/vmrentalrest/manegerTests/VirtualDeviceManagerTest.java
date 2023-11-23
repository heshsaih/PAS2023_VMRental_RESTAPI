package com.example.vmrentalrest.manegerTests;

import com.example.vmrentalrest.DBManagementTools;
import com.example.vmrentalrest.dto.updatedto.UpdateVirtualDeviceDTO;
import com.example.vmrentalrest.exceptions.IllegalOperationException;
import com.example.vmrentalrest.exceptions.RecordNotFoundException;
import com.example.vmrentalrest.managers.VirtualDeviceManager;
import com.example.vmrentalrest.model.enums.DatabaseType;
import com.example.vmrentalrest.model.enums.OperatingSystemType;

import com.example.vmrentalrest.model.enums.VirtualDeviceType;
import com.example.vmrentalrest.model.virtualdevices.VirtualDatabaseServer;
import com.example.vmrentalrest.model.virtualdevices.VirtualMachine;
import com.example.vmrentalrest.model.virtualdevices.VirtualPhone;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class VirtualDeviceManagerTest {
    @Autowired
    VirtualDeviceManager virtualDeviceManager;
    @Autowired
    DBManagementTools dbManagementTools;
    private void addVirtualDevices() {
        dbManagementTools.createData();
    }
    @Test
    @Transactional
    void findAllVirtualDeviceTest() {
        int virtualDeviceBuffer = virtualDeviceManager.findAllVirtualDevices().size();
        addVirtualDevices();
        Assertions.assertThat(virtualDeviceBuffer + 3 == virtualDeviceManager.findAllVirtualDevices().size()).isTrue();
    }
    @Test
    @Transactional
    void deleteVirtualDeviceTest() {
        addVirtualDevices();
        int virtualDeviceBuffer = virtualDeviceManager.findAllVirtualDevices().size();
        String bufferedId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        Assertions.assertThatThrownBy(() -> virtualDeviceManager.deleteVirtualDevice(bufferedId)).isInstanceOf(IllegalOperationException.class);
        Assertions.assertThat(virtualDeviceManager.findAllVirtualDevices().size() == virtualDeviceBuffer).isTrue();
        int virtualDeviceBuffer2 = virtualDeviceManager.findAllVirtualDevices().size();
        String bufferedId2 = virtualDeviceManager.findAllVirtualDevices().get(2).getId();
        virtualDeviceManager.deleteVirtualDevice(bufferedId2);
        Assertions.assertThat(virtualDeviceManager.findAllVirtualDevices().size() == virtualDeviceBuffer2 - 1).isTrue();
        Assertions.assertThatThrownBy(() -> virtualDeviceManager.findVirtualDeviceById(bufferedId2)).isInstanceOf(RecordNotFoundException.class);
    }
    @Test
    @Transactional
    void getVirtualDeviceTest() {
        addVirtualDevices();
        Assertions.assertThat(virtualDeviceManager.findAllVirtualDevices().get(0)
                .equals(virtualDeviceManager.findVirtualDeviceById(virtualDeviceManager.findAllVirtualDevices().get(0).getId()))).isTrue();
    }

    @Test
    @Transactional
    void updateVirtualDeviceTest() {
        addVirtualDevices();
        UpdateVirtualDeviceDTO virtualDatabaseServer = new UpdateVirtualDeviceDTO(512,24,32);
        virtualDeviceManager.updateVirtualDevice(virtualDeviceManager.findAllVirtualDevices().get(0).getId(),virtualDatabaseServer);
        virtualDeviceManager.updateDatabaseType(virtualDeviceManager.findAllVirtualDevices().get(0).getId(),DatabaseType.ORACLE);
        var bufferedId1 = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.updateVirtualDevice(bufferedId1,virtualDatabaseServer);
        VirtualDatabaseServer virtualDatabaseServer1 = (VirtualDatabaseServer) virtualDeviceManager.findAllVirtualDevices().get(0);
        Assertions.assertThat(virtualDatabaseServer1.getCpuCores() == 24).isTrue();
        Assertions.assertThat(virtualDatabaseServer1.getRam() == 32).isTrue();
        Assertions.assertThat(virtualDatabaseServer1.getStorageSize() == 512).isTrue();
        Assertions.assertThat((virtualDatabaseServer1.getDatabaseType() == DatabaseType.ORACLE)).isTrue();
        UpdateVirtualDeviceDTO virtualMachineDTO = new UpdateVirtualDeviceDTO(64,48,0);
        var bufferedId2 = virtualDeviceManager.findAllVirtualDevices().get(1).getId();
        virtualDeviceManager.updateOperatingSystemType(bufferedId2, OperatingSystemType.WINDOWS);
        virtualDeviceManager.updateVirtualDevice(bufferedId2,virtualMachineDTO);
        VirtualMachine virtualMachine1 = (VirtualMachine) virtualDeviceManager.findAllVirtualDevices().get(1);
        Assertions.assertThat(virtualMachine1.getCpuCores() == 48).isTrue();
        Assertions.assertThat(virtualMachine1.getStorageSize() == 64).isTrue();
        Assertions.assertThat((virtualMachine1.getOperatingSystemType() == OperatingSystemType.WINDOWS)).isTrue();
        UpdateVirtualDeviceDTO virtualPhoneDTO = new UpdateVirtualDeviceDTO(-8,32,1024);
        VirtualPhone virtualPhone = new VirtualPhone();
        UpdateVirtualDeviceDTO virtualPhone1 = new UpdateVirtualDeviceDTO(128,32,1024);
        virtualPhone.setCpuCores(8);
        virtualPhone.setRam(32);
        virtualPhone.setStorageSize(1024);
        virtualPhone.setPhoneNumber(987654321);
        var bufferedId3 = virtualDeviceManager.findAllVirtualDevices().get(2).getId();
        virtualDeviceManager.updateVirtualDevice(bufferedId3,virtualPhone1);
        UpdateVirtualDeviceDTO virtualPhone2 = new UpdateVirtualDeviceDTO(-8,32,1024);
        virtualDeviceManager.updatePhoneNumber(bufferedId3,123456789);
        Assertions.assertThat(virtualDeviceManager.findVirtualDeviceById(bufferedId3).getCpuCores() == -8).isFalse();
        UpdateVirtualDeviceDTO virtualMachine2 = new UpdateVirtualDeviceDTO(8,32,-1024);
        Assertions.assertThatThrownBy(()->virtualDeviceManager.updateVirtualDevice(bufferedId2,virtualMachine2))
                .isInstanceOf(IllegalOperationException.class);
        Assertions.assertThat(virtualDeviceManager.findVirtualDeviceById(bufferedId2).getStorageSize() == -2048).isFalse();

    }


}

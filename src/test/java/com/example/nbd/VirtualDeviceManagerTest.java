package com.example.nbd;

import com.example.nbd.exceptions.DeviceHasAllocationException;
import com.example.nbd.exceptions.invalidParametersExceptions.InvalidVirtualDeviceException;
import com.example.nbd.exceptions.recordNotFoundExceptions.VirtualDeviceNotFoundException;
import com.example.nbd.managers.VirtualDeviceManager;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;

import com.example.nbd.model.enums.VirtualDeviceType;
import com.example.nbd.model.virtualdevices.VirtualDatabaseServer;
import com.example.nbd.model.virtualdevices.VirtualMachine;
import com.example.nbd.model.virtualdevices.VirtualPhone;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class VirtualDeviceManagerTest {
    @Autowired
    VirtualDeviceManager virtualDeviceManager;

    private void addVirtualDevices() throws InvalidVirtualDeviceException {
        VirtualDatabaseServer virtualDatabaseServer = new VirtualDatabaseServer();
        virtualDatabaseServer.setCpuCores(16);
        virtualDatabaseServer.setRam(64);
        virtualDatabaseServer.setStorageSize(2048);
        virtualDatabaseServer.setDatabase(DatabaseType.MONGODB);
        virtualDeviceManager.createVirtualDevice(virtualDatabaseServer, VirtualDeviceType.VIRTUAL_DATABASE_SERVER);
        VirtualMachine virtualMachine = new VirtualMachine();
        virtualMachine.setCpuCores(8);
        virtualMachine.setRam(32);
        virtualMachine.setStorageSize(1024);
        virtualMachine.setOperatingSystemType(OperatingSystemType.FEDORA);
        virtualDeviceManager.createVirtualDevice(virtualMachine, VirtualDeviceType.VIRTUAL_MACHINE);
        VirtualPhone virtualPhone = new VirtualPhone();
        virtualPhone.setCpuCores(4);
        virtualPhone.setRam(16);
        virtualPhone.setStorageSize(512);
        virtualPhone.setPhoneNumber(123456789);
        virtualDeviceManager.createVirtualDevice(virtualPhone, VirtualDeviceType.VIRTUAL_PHONE);
    }
    @Test
    @Transactional
    void createVirtualDeviceTest() throws InvalidVirtualDeviceException{
        addVirtualDevices();
        int buffer = virtualDeviceManager.findAllVirtualDevices().size();
        VirtualDatabaseServer virtualDatabaseServer = new VirtualDatabaseServer();
        virtualDatabaseServer.setCpuCores(16);
        virtualDatabaseServer.setRam(64);
        virtualDatabaseServer.setDatabase(DatabaseType.MONGODB);
        Assertions.assertThatThrownBy(() -> virtualDeviceManager.createVirtualDevice(virtualDatabaseServer, VirtualDeviceType.VIRTUAL_DATABASE_SERVER))
                .isInstanceOf(InvalidVirtualDeviceException.class);
        VirtualMachine virtualMachine = new VirtualMachine();
        virtualMachine.setCpuCores(8);
        virtualMachine.setRam(32);
        virtualMachine.setStorageSize(1024);
        Assertions.assertThatThrownBy(() -> virtualDeviceManager.createVirtualDevice(virtualMachine, VirtualDeviceType.VIRTUAL_MACHINE))
                .isInstanceOf(InvalidVirtualDeviceException.class);
        Assertions.assertThatThrownBy(() -> virtualDeviceManager.createVirtualDevice(null, null))
                .isInstanceOf(InvalidVirtualDeviceException.class);
        Assertions.assertThatThrownBy(() -> virtualDeviceManager.createVirtualDevice(null, VirtualDeviceType.VIRTUAL_MACHINE))
                .isInstanceOf(InvalidVirtualDeviceException.class);
        Assertions.assertThatThrownBy(() -> virtualDeviceManager.createVirtualDevice(virtualMachine, null))
                .isInstanceOf(InvalidVirtualDeviceException.class);
        Assertions.assertThat(virtualDeviceManager.findAllVirtualDevices().size() == buffer).isTrue();

    }
    @Test
    @Transactional
    void findAllVirtualDeviceTest() throws InvalidVirtualDeviceException {
        int virtualDeviceBuffer = virtualDeviceManager.findAllVirtualDevices().size();
        addVirtualDevices();
        Assertions.assertThat(virtualDeviceBuffer + 3 == virtualDeviceManager.findAllVirtualDevices().size()).isTrue();
    }
    @Test
    @Transactional
    void deleteVirtualDeviceTest() throws InvalidVirtualDeviceException, VirtualDeviceNotFoundException, DeviceHasAllocationException {
        addVirtualDevices();
        int virtualDeviceBuffer = virtualDeviceManager.findAllVirtualDevices().size();
        String bufferedId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.deleteVirtualDevice(bufferedId);
        Assertions.assertThat(virtualDeviceManager.findAllVirtualDevices().size() == virtualDeviceBuffer - 1).isTrue();
        Assertions.assertThatThrownBy(() -> virtualDeviceManager.findVirtualDeviceById(bufferedId)).isInstanceOf(VirtualDeviceNotFoundException.class);
        Assertions.assertThatThrownBy(() -> virtualDeviceManager.deleteVirtualDevice(bufferedId)).isInstanceOf(VirtualDeviceNotFoundException.class);
    }
    @Test
    @Transactional
    void getVirtualDeviceTest() throws InvalidVirtualDeviceException, VirtualDeviceNotFoundException {
        addVirtualDevices();
        Assertions.assertThat(virtualDeviceManager.findAllVirtualDevices().get(0)
                .equals(virtualDeviceManager.findVirtualDeviceById(virtualDeviceManager.findAllVirtualDevices().get(0).getId()))).isTrue();
    }

    @Test
    @Transactional
    void updateVirtualDeviceTest() throws VirtualDeviceNotFoundException, InvalidVirtualDeviceException {
        addVirtualDevices();
        VirtualDatabaseServer virtualDatabaseServer = new VirtualDatabaseServer();
        virtualDatabaseServer.setCpuCores(24);
        virtualDatabaseServer.setRam(32);
        virtualDatabaseServer.setStorageSize(4096);
        virtualDatabaseServer.setDatabase(DatabaseType.ORACLE);
        var bufferedId1 = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.updateVirtualDevice(bufferedId1,virtualDatabaseServer);
        VirtualDatabaseServer virtualDatabaseServer1 = (VirtualDatabaseServer) virtualDeviceManager.findAllVirtualDevices().get(0);
        Assertions.assertThat(virtualDatabaseServer1.getCpuCores() == 24).isTrue();
        Assertions.assertThat(virtualDatabaseServer1.getRam() == 32).isTrue();
        Assertions.assertThat(virtualDatabaseServer1.getStorageSize() == 4096).isTrue();
        Assertions.assertThat((virtualDatabaseServer1.getDatabase() == DatabaseType.ORACLE)).isTrue();
        VirtualMachine virtualMachine = new VirtualMachine();
        virtualMachine.setCpuCores(16);
        virtualMachine.setStorageSize(2048);
        virtualMachine.setOperatingSystemType(OperatingSystemType.WINDOWS);
        var bufferedId2 = virtualDeviceManager.findAllVirtualDevices().get(1).getId();
        virtualDeviceManager.updateVirtualDevice(bufferedId2,virtualMachine);
        VirtualMachine virtualMachine1 = (VirtualMachine) virtualDeviceManager.findAllVirtualDevices().get(1);
        Assertions.assertThat(virtualMachine1.getCpuCores() == 16).isTrue();
        Assertions.assertThat(virtualMachine1.getStorageSize() == 2048).isTrue();
        Assertions.assertThat((virtualMachine1.getOperatingSystemType() == OperatingSystemType.WINDOWS)).isTrue();
        VirtualPhone virtualPhone = new VirtualPhone();
        virtualPhone.setCpuCores(8);
        virtualPhone.setRam(32);
        virtualPhone.setStorageSize(1024);
        virtualPhone.setPhoneNumber(987654321);
        var bufferedId3 = virtualDeviceManager.findAllVirtualDevices().get(2).getId();
        virtualDeviceManager.updateVirtualDevice(bufferedId3,virtualPhone);
        VirtualPhone virtualPhone1 = (VirtualPhone) virtualDeviceManager.findAllVirtualDevices().get(2);
        Assertions.assertThat(virtualPhone1.getCpuCores() == 8).isTrue();
        Assertions.assertThat(virtualPhone1.getRam() == 32).isTrue();
        Assertions.assertThat(virtualPhone1.getStorageSize() == 1024).isTrue();
        Assertions.assertThat((virtualPhone1.getPhoneNumber() == 987654321)).isTrue();
        Assertions.assertThatThrownBy(() -> virtualDeviceManager.updateVirtualDevice("123",virtualPhone))
                .isInstanceOf(VirtualDeviceNotFoundException.class);
        VirtualPhone virtualPhone2 = new VirtualPhone();
        virtualPhone2.setCpuCores(-8);
        virtualPhone2.setRam(32);
        virtualPhone2.setStorageSize(1024);
        virtualDeviceManager.updateVirtualDevice(bufferedId3,virtualPhone2);
        Assertions.assertThat(virtualDeviceManager.findVirtualDeviceById(bufferedId3).getCpuCores() == -8).isFalse();
        VirtualMachine virtualMachine2 = new VirtualMachine();
        virtualMachine2.setCpuCores(8);
        virtualMachine2.setStorageSize(-2048);
        virtualDeviceManager.updateVirtualDevice(bufferedId2,virtualMachine2);
        Assertions.assertThat(virtualDeviceManager.findVirtualDeviceById(bufferedId2).getStorageSize() == -2048).isFalse();

    }


}

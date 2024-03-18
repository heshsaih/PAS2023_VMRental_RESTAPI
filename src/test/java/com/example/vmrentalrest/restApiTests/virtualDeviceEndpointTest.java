package com.example.vmrentalrest.restApiTests;

import com.example.vmrentalrest.DBManagementTools;
import com.example.vmrentalrest.dto.SignInDTO;
import com.example.vmrentalrest.dto.updatedto.UpdateVirtualDeviceDTO;
import com.example.vmrentalrest.endpoints.VirtualDeviceEndpoint;
import com.example.vmrentalrest.managers.VirtualDeviceManager;
import com.example.vmrentalrest.model.enums.DatabaseType;
import com.example.vmrentalrest.model.enums.VirtualDeviceType;
import com.example.vmrentalrest.model.virtualdevices.VirtualDatabaseServer;
import com.example.vmrentalrest.model.virtualdevices.VirtualDevice;
import com.example.vmrentalrest.model.virtualdevices.VirtualMachine;
import com.example.vmrentalrest.model.virtualdevices.VirtualPhone;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class virtualDeviceEndpointTest {
    @Autowired
    DBManagementTools dbManagementTools;
    @Autowired
    VirtualDeviceEndpoint virtualDeviceEndpoint;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    VirtualDeviceManager virtualDeviceManager;
    @Autowired
    ObjectMapper objectMapper;
    private String token;
    private String etag;

    private void setup() throws Exception {
        SignInDTO credentials = new SignInDTO();
        credentials.setUsername("admin");
        credentials.setPassword("admin123");
        var response = mockMvc.perform(post("/users/signin")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(credentials)))
                .andReturn().getResponse();
        token = response.getContentAsString().substring(10, response.getContentAsString().length() - 2);
        etag = response.getHeader("ETag");
    }
    @Test
    @Transactional
    void getAllVirtualDevicesTest() throws Exception {
        dbManagementTools.createData();
        setup();
        var devices = virtualDeviceManager.findAllVirtualDevices();
        var device1 = devices.get(0);
        var device2 = devices.get(1);
        var device3 = devices.get(2);
        mockMvc.perform(get("/virtual-devices").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(device1.getId()))
                .andExpect(jsonPath("$[0].cpuCores").value(device1.getCpuCores()))
                .andExpect(jsonPath("$[0].ram").value(device1.getRam()))
                .andExpect(jsonPath("$[0].storageSize").value(device1.getStorageSize()))
                .andExpect(jsonPath("$[0].databaseType").value(((VirtualDatabaseServer)device1).getDatabaseType().toString()))
                .andExpect(jsonPath("$[1].id").value(device2.getId()))
                .andExpect(jsonPath("$[1].cpuCores").value(device2.getCpuCores()))
                .andExpect(jsonPath("$[1].ram").value(device2.getRam()))
                .andExpect(jsonPath("$[1].storageSize").value(device2.getStorageSize()))
                .andExpect(jsonPath("$[1].operatingSystemType").value(((VirtualMachine)device2).getOperatingSystemType().toString()))
                .andExpect(jsonPath("$[2].id").value(device3.getId()))
                .andExpect(jsonPath("$[2].cpuCores").value(device3.getCpuCores()))
                .andExpect(jsonPath("$[2].ram").value(device3.getRam()))
                .andExpect(jsonPath("$[2].storageSize").value(device3.getStorageSize()))
                .andExpect(jsonPath("$[2].phoneNumber").value(((VirtualPhone)device3).getPhoneNumber()));

    }
    @Test
    @Transactional
    void getVirtualDeviceByIdTest() throws Exception {
        dbManagementTools.createData();
        setup();
        var devices = virtualDeviceManager.findAllVirtualDevices();
        VirtualDevice device1 = devices.get(0);
        VirtualDevice device2 = devices.get(1);
        VirtualDevice device3 = devices.get(2);
        mockMvc.perform(get("/virtual-devices/" + device1.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(device1.getId()))
                .andExpect(jsonPath("$.cpuCores").value(device1.getCpuCores()))
                .andExpect(jsonPath("$.ram").value(device1.getRam()))
                .andExpect(jsonPath("$.storageSize").value(device1.getStorageSize()))
                .andExpect(jsonPath("$.databaseType").value(((VirtualDatabaseServer)device1).getDatabaseType().toString()));
        mockMvc.perform(get("/virtual-devices/" + device2.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(device2.getId()))
                .andExpect(jsonPath("$.cpuCores").value(device2.getCpuCores()))
                .andExpect(jsonPath("$.ram").value(device2.getRam()))
                .andExpect(jsonPath("$.storageSize").value(device2.getStorageSize()))
                .andExpect(jsonPath("$.operatingSystemType").value(((VirtualMachine)device2).getOperatingSystemType().toString()));
        mockMvc.perform(get("/virtual-devices/" + device3.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(device3.getId()))
                .andExpect(jsonPath("$.cpuCores").value(device3.getCpuCores()))
                .andExpect(jsonPath("$.ram").value(device3.getRam()))
                .andExpect(jsonPath("$.storageSize").value(device3.getStorageSize()))
                .andExpect(jsonPath("$.phoneNumber").value(((VirtualPhone)device3).getPhoneNumber()));
        mockMvc.perform(get("/virtual-devices/123").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isNotFound());
    }
    @Test
    @Transactional
    void createVirtualDeviceTest() throws Exception {
        dbManagementTools.createData();
        setup();
        VirtualDatabaseServer virtualDatabaseServerDTO = new VirtualDatabaseServer();
        virtualDatabaseServerDTO.setCpuCores(24);
        virtualDatabaseServerDTO.setRam(32);
        virtualDatabaseServerDTO.setStorageSize(1024);
        virtualDatabaseServerDTO.setDatabaseType(DatabaseType.ORACLE);
        VirtualMachine virtualMachineDTO = new VirtualMachine();
        virtualMachineDTO.setCpuCores(16);
        virtualMachineDTO.setStorageSize(512);
        virtualMachineDTO.setRam(64);
        virtualMachineDTO.setOperatingSystemType(com.example.vmrentalrest.model.enums.OperatingSystemType.WINDOWS);
        VirtualPhone virtualPhoneDTO = new VirtualPhone();
        virtualPhoneDTO.setCpuCores(8);
        virtualPhoneDTO.setRam(32);
        virtualPhoneDTO.setStorageSize(1024);
        virtualPhoneDTO.setPhoneNumber(987654321);
        mockMvc.perform(post("/virtual-devices/createvirtual-database-server").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(virtualDatabaseServerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpuCores").value(virtualDatabaseServerDTO.getCpuCores()))
                .andExpect(jsonPath("$.ram").value(virtualDatabaseServerDTO.getRam()))
                .andExpect(jsonPath("$.storageSize").value(virtualDatabaseServerDTO.getStorageSize()))
                .andExpect(jsonPath("$.databaseType").value(virtualDatabaseServerDTO.getDatabaseType().toString()));
        mockMvc.perform(post("/virtual-devices/createvirtual-database-server").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(virtualDatabaseServerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpuCores").value(virtualDatabaseServerDTO.getCpuCores()))
                .andExpect(jsonPath("$.ram").value(virtualDatabaseServerDTO.getRam()))
                .andExpect(jsonPath("$.storageSize").value(virtualDatabaseServerDTO.getStorageSize()))
                .andExpect(jsonPath("$.databaseType").value(virtualDatabaseServerDTO.getDatabaseType().toString()));
        mockMvc.perform(post("/virtual-devices/createvirtual-machine").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(virtualMachineDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpuCores").value(virtualMachineDTO.getCpuCores()))
                .andExpect(jsonPath("$.ram").value(virtualMachineDTO.getRam()))
                .andExpect(jsonPath("$.storageSize").value(virtualMachineDTO.getStorageSize()))
                .andExpect(jsonPath("$.operatingSystemType").value(virtualMachineDTO.getOperatingSystemType().toString()));
        mockMvc.perform(post("/virtual-devices/createvirtual-phone").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(virtualPhoneDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpuCores").value(virtualPhoneDTO.getCpuCores()))
                .andExpect(jsonPath("$.ram").value(virtualPhoneDTO.getRam()))
                .andExpect(jsonPath("$.storageSize").value(virtualPhoneDTO.getStorageSize()))
                .andExpect(jsonPath("$.phoneNumber").value(virtualPhoneDTO.getPhoneNumber()));
        virtualMachineDTO.setCpuCores(-3);
        mockMvc.perform(post("/virtual-devices/createvirtual-machine").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(virtualMachineDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @Transactional
    void updateVirtualDevice() throws Exception{
        dbManagementTools.createData();
        setup();
        UpdateVirtualDeviceDTO virtualDatabaseServerDTO = new UpdateVirtualDeviceDTO(1024,8,16);
        VirtualDatabaseServer virtualDatabaseServer = (VirtualDatabaseServer) virtualDeviceManager.findAllVirtualDevices().get(0);
        mockMvc.perform(put("/virtual-devices/" + virtualDatabaseServer.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(virtualDatabaseServerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(virtualDatabaseServer.getId()))
                .andExpect(jsonPath("$.cpuCores").value(virtualDatabaseServerDTO.cpuCores()))
                .andExpect(jsonPath("$.ram").value(virtualDatabaseServerDTO.ram()))
                .andExpect(jsonPath("$.storageSize").value(virtualDatabaseServerDTO.storageSize()));
        Assertions.assertTrue(virtualDeviceManager.findVirtualDeviceById(virtualDatabaseServer.getId()).getCpuCores() == virtualDatabaseServerDTO.cpuCores());
        Assertions.assertTrue(virtualDeviceManager.findVirtualDeviceById(virtualDatabaseServer.getId()).getRam() == virtualDatabaseServerDTO.ram());
        Assertions.assertTrue(virtualDeviceManager.findVirtualDeviceById(virtualDatabaseServer.getId()).getStorageSize() == virtualDatabaseServerDTO.storageSize());
        mockMvc.perform(put("/virtual-devices/123").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(virtualDatabaseServerDTO)))
                .andExpect(status().isNotFound());
        virtualDatabaseServer = (VirtualDatabaseServer) virtualDeviceManager.findAllVirtualDevices().get(0);
        UpdateVirtualDeviceDTO virtualDatabaseServerDTO2 = new UpdateVirtualDeviceDTO(-1024,-8,16);
        mockMvc.perform(put("/virtual-devices/" + virtualDatabaseServer.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(virtualDatabaseServerDTO2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(virtualDatabaseServer.getId()))
                .andExpect(jsonPath("$.cpuCores").value(virtualDatabaseServer.getCpuCores()))
                .andExpect(jsonPath("$.ram").value(virtualDatabaseServer.getRam()))
                .andExpect(jsonPath("$.storageSize").value(virtualDatabaseServer.getStorageSize()))
                .andExpect(jsonPath("$.databaseType").value(virtualDatabaseServer.getDatabaseType().toString()));
        UpdateVirtualDeviceDTO virtualDatabaseServerDTO3 = new UpdateVirtualDeviceDTO(32132132,-8,16);
        mockMvc.perform(put("/virtual-devices/" + virtualDatabaseServer.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(virtualDatabaseServerDTO3)))
                .andExpect(status().isBadRequest());

    }
    @Test
    @Transactional
    void deleteVirtualDevice() throws Exception {
        dbManagementTools.createData();
        setup();
        var devices = virtualDeviceManager.findAllVirtualDevices();
        VirtualDevice device1 = devices.get(0);
        VirtualDevice device2 = devices.get(1);
        VirtualDevice device3 = devices.get(2);
        mockMvc.perform(delete("/virtual-devices/" + device1.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isBadRequest());
        mockMvc.perform(delete("/virtual-devices/" + device2.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/virtual-devices/" + device3.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/virtual-devices/" + device3.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/virtual-devices/123").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isNotFound());
        Assertions.assertTrue(virtualDeviceManager.findAllVirtualDevices().size() == 1);
    }
}

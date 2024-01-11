package com.example.vmrentalrest.restApiTests;

import com.example.vmrentalrest.DBManagementTools;
import com.example.vmrentalrest.dto.updatedto.UpdateRentDTO;
import com.example.vmrentalrest.endpoints.RentEndpoint;
import com.example.vmrentalrest.managers.RentManager;
import com.example.vmrentalrest.managers.UserManager;
import com.example.vmrentalrest.managers.VirtualDeviceManager;
import com.example.vmrentalrest.model.Rent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class rentEndpointTest {
    @Autowired
    DBManagementTools dbManagementTools;
    @Autowired
    RentEndpoint rentEndpoint;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    RentManager rentManager;
    @Autowired
    UserManager userManager;
    @Autowired
    VirtualDeviceManager virtualDeviceManager;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Transactional
    void getAllRentsTest() throws Exception {
        dbManagementTools.createData();
        var rents = rentManager.findAllRents();
        Rent rent1 = rents.get(0);
        Rent rent2 = rents.get(1);
        mockMvc.perform(get("/rents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userId").value(rent1.getUserId()))
                .andExpect(jsonPath("$[0].virtualDeviceId").value(rent1.getVirtualDeviceId()))
//                .andExpect(jsonPath("$[0].startLocalDateTime").value(rent1.getStartLocalDateTime().toString()))
//                .andExpect(jsonPath("$[0].endLocalDateTime").value(rent1.getEndLocalDateTime().toString()))
                .andExpect(jsonPath("$[1].userId").value(rent2.getUserId()))
                .andExpect(jsonPath("$[1].virtualDeviceId").value(rent2.getVirtualDeviceId()));
//                .andExpect(jsonPath("$[1].startLocalDateTime").value(rent2.getStartLocalDateTime().toString()))
//                .andExpect(jsonPath("$[1].endLocalDateTime").value(rent2.getEndLocalDateTime().toString()));
    }

    @Test
    @Transactional
    void getRentByIdTest() throws Exception {
        dbManagementTools.createData();
        var rents = rentManager.findAllRents();
        Rent rent1 = rents.get(0);
        Rent rent2 = rents.get(1);
        mockMvc.perform(get("/rents/" + rent1.getRentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(rent1.getUserId()))
                .andExpect(jsonPath("$.virtualDeviceId").value(rent1.getVirtualDeviceId()));
//                .andExpect(objectMapper.jsonPath("$.startLocalDateTime").value(rent1.getStartLocalDateTime().toString()));
//                .andExpect(jsonPath("$.endLocalDateTime").value(rent1.getEndLocalDateTime().toString()));
        mockMvc.perform(get("/rents/" + rent2.getRentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(rent2.getUserId()))
                .andExpect(jsonPath("$.virtualDeviceId").value(rent2.getVirtualDeviceId()));
//                .andExpect(jsonPath("$.startLocalDateTime").value(rent2.getStartLocalDateTime().toString()))
//                .andExpect(jsonPath("$.endLocalDateTime").value(rent2.getEndLocalDateTime().toString()));
        mockMvc.perform(get("/rents/" + "nonExistingId"))
                .andExpect(status().isNotFound());
    }
    @Test
    @Transactional
    void getRentsByVirtualDeviceId() throws Exception {
        dbManagementTools.createData();
        Rent rent1 = rentManager.findAllRents().get(0);
        Rent rent2 = rentManager.findAllRents().get(1);
        mockMvc.perform(get("/rents/getbyvirtualdeviceid/" + rent1.getVirtualDeviceId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userId").value(rent1.getUserId()))
                .andExpect(jsonPath("$[0].virtualDeviceId").value(rent1.getVirtualDeviceId()))
//                .andExpect(jsonPath("$[0].startLocalDateTime").value(rent1.getStartLocalDateTime().toString()))
//                .andExpect(jsonPath("$[0].endLocalDateTime").value(rent1.getEndLocalDateTime().toString()))
                .andExpect(jsonPath("$[1].userId").value(rent2.getUserId()))
                .andExpect(jsonPath("$[1].virtualDeviceId").value(rent2.getVirtualDeviceId()));
//                .andExpect(jsonPath("$[1].startLocalDateTime").value(rent2.getStartLocalDateTime().toString()))
//                .andExpect(jsonPath("$[1].endLocalDateTime").value(rent2.getEndLocalDateTime().toString()));
        mockMvc.perform(get("/rents/getbyvirtualdeviceid/" + "test123"))
                .andExpect(status().isNotFound());
    }
    @Test
    @Transactional
    void getRentByUserId() throws Exception {
        dbManagementTools.createData();
        Rent rent1 = rentManager.findAllRents().get(0);
        String clientID = rent1.getUserId();
        mockMvc.perform(get("/rents/getbyuserid/" +clientID ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userId").value(rent1.getUserId()))
                .andExpect(jsonPath("$[0].virtualDeviceId").value(rent1.getVirtualDeviceId()));
//                .andExpect(jsonPath("$[0].startLocalDateTime").value(rent1.getStartLocalDateTime().toString()))
//                .andExpect(jsonPath("$[0].endLocalDateTime").value(rent1.getEndLocalDateTime().toString()))
        mockMvc.perform(get("/rents/getbyuserid/" + "test321"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void createRentTest() throws Exception {
        dbManagementTools.createData();
        int rentSizeBuffer = rentManager.findAllRents().size();
        Rent rent = new Rent();
        var users = userManager.findAllUsers();
        var virtualDevices = virtualDeviceManager.findAllVirtualDevices();
        rent.setUserId(users.get(1).getId());
        rent.setVirtualDeviceId(virtualDevices.get(2).getId());
        rent.setStartLocalDateTime(LocalDateTime.now());
        rent.setEndLocalDateTime(LocalDateTime.now().plusDays(1));
        mockMvc.perform(post("/rents")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(rent.getUserId()))
                .andExpect(jsonPath("$.virtualDeviceId").value(rent.getVirtualDeviceId()));
        Rent rent2 = new Rent();
        rent2.setUserId(users.get(0).getId());
        rent2.setVirtualDeviceId(virtualDevices.get(0).getId());
        rent2.setStartLocalDateTime(LocalDateTime.now().minusWeeks(2));
        rent2.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3));
        mockMvc.perform(post("/rents")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rent2)))
                .andExpect(status().isBadRequest());
        Rent rent3 = new Rent();
        rent3.setUserId(users.get(0).getId());
        rent3.setVirtualDeviceId(virtualDevices.get(0).getId());
        rent3.setStartLocalDateTime(LocalDateTime.now().plusWeeks(2));
        rent3.setEndLocalDateTime(LocalDateTime.now().plusWeeks(1));
        mockMvc.perform(post("/rents")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rent3)))
                .andExpect(status().isBadRequest());
        Rent rent4 = new Rent();
        rent4.setUserId("NonExistent");
        rent4.setVirtualDeviceId(virtualDevices.get(0).getId());
        rent4.setStartLocalDateTime(LocalDateTime.now().plusWeeks(2));
        rent4.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3));
        mockMvc.perform(post("/rents")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rent4)))
                .andExpect(status().isNotFound());
        Rent rent5 = new Rent();
        rent5.setUserId(users.get(2).getId());
        rent5.setVirtualDeviceId("NonExistent");
        rent5.setStartLocalDateTime(LocalDateTime.now().plusWeeks(2));
        rent5.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3));
        mockMvc.perform(post("/rents")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rent5)))
                .andExpect(status().isNotFound());
        Assertions.assertTrue(rentSizeBuffer + 1== rentManager.findAllRents().size());
    }
    @Test
    @Transactional
    void updateRentTest() throws Exception {
        dbManagementTools.createData();
        Rent rent = rentManager.findAllRents().get(0);
        UpdateRentDTO updateRentDTO = new UpdateRentDTO(LocalDateTime.now().plusWeeks(2),LocalDateTime.now().plusWeeks(3));
        mockMvc.perform(put("/rents/" + rent.getRentId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(rent.getUserId()))
                .andExpect(jsonPath("$.virtualDeviceId").value(rent.getVirtualDeviceId()));
        mockMvc.perform(put("/rents/" + rent.getRentId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
        Rent rent2 = rentManager.findAllRents().get(0);
        UpdateRentDTO updateRentDTO2 = new UpdateRentDTO(LocalDateTime.now().minusWeeks(2),LocalDateTime.now().plusWeeks(1));

        Rent rent3 = rentManager.findAllRents().get(0);
        mockMvc.perform(put("/rents/" + rent3.getRentId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rent3)))
                .andExpect(status().isOk());
        mockMvc.perform(put("/rents/" + "NonExistent")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rent3)))
                .andExpect(status().isNotFound());
    }
    @Test
    @Transactional
    void deleteRentTest() throws Exception {
        dbManagementTools.createData();
        int rentSizeBuffer = rentManager.findAllRents().size();
        Rent rent = rentManager.findAllRents().get(0);
        Rent rent2 = rentManager.findAllRents().get(1);
        mockMvc.perform(delete("/rents/" + rent.getRentId()))
                .andExpect(status().isOk());
        Assertions.assertTrue(rentSizeBuffer - 1 == rentManager.findAllRents().size());
        mockMvc.perform(delete("/rents/" + "NonExistent"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/rents/" + rent.getRentId()))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/rents/" + rent2.getRentId()))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(rentSizeBuffer - 1 == rentManager.findAllRents().size());

    }

}

package com.example.vmrentalrest.restApiTests;

import com.example.vmrentalrest.DBManagementTools;
import com.example.vmrentalrest.endpoints.RentEndpoint;
import com.example.vmrentalrest.endpoints.UserEndpoint;
import com.example.vmrentalrest.managers.RentManager;
import com.example.vmrentalrest.managers.UserManager;
import com.example.vmrentalrest.managers.VirtualDeviceManager;
import com.example.vmrentalrest.model.Rent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
        Rent rent = new Rent();
       // var users = userManager
    }

}

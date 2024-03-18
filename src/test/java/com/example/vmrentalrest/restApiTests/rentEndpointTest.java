package com.example.vmrentalrest.restApiTests;

import com.example.vmrentalrest.DBManagementTools;
import com.example.vmrentalrest.PAS2023_VMRENTAL_RESTAPI_Application;
import com.example.vmrentalrest.dto.SignInDTO;
import com.example.vmrentalrest.dto.updatedto.UpdateRentDTO;
import com.example.vmrentalrest.endpoints.RentEndpoint;
import com.example.vmrentalrest.managers.RentManager;
import com.example.vmrentalrest.managers.UserManager;
import com.example.vmrentalrest.managers.VirtualDeviceManager;
import com.example.vmrentalrest.model.Rent;
import com.example.vmrentalrest.model.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
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
    void getAllRentsTest() throws Exception {
        dbManagementTools.createData();
        setup();
        var rents = rentManager.findAllRents();
        Rent rent1 = rents.get(0);
        Rent rent2 = rents.get(1);
        mockMvc.perform(get("/rents").
                        header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
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
        setup();
        var rents = rentManager.findAllRents();
        Rent rent1 = rents.get(0);
        Rent rent2 = rents.get(1);
        mockMvc.perform(get("/rents/" + rent1.getRentId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(rent1.getUserId()))
                .andExpect(jsonPath("$.virtualDeviceId").value(rent1.getVirtualDeviceId()));
//                .andExpect(objectMapper.jsonPath("$.startLocalDateTime").value(rent1.getStartLocalDateTime().toString()));
//                .andExpect(jsonPath("$.endLocalDateTime").value(rent1.getEndLocalDateTime().toString()));
        mockMvc.perform(get("/rents/" + rent2.getRentId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(rent2.getUserId()))
                .andExpect(jsonPath("$.virtualDeviceId").value(rent2.getVirtualDeviceId()));
//                .andExpect(jsonPath("$.startLocalDateTime").value(rent2.getStartLocalDateTime().toString()))
//                .andExpect(jsonPath("$.endLocalDateTime").value(rent2.getEndLocalDateTime().toString()));
        mockMvc.perform(get("/rents/" + "nonExistingId").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isNotFound());
    }
    @Test
    @Transactional
    void getRentsByVirtualDeviceId() throws Exception {
        dbManagementTools.createData();
        setup();
        Rent rent1 = rentManager.findAllRents().get(0);
        Rent rent2 = rentManager.findAllRents().get(1);
        mockMvc.perform(get("/rents/getbyvirtualdeviceid/" + rent1.getVirtualDeviceId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
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
        mockMvc.perform(get("/rents/getbyvirtualdeviceid/" + "test123").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isNotFound());
    }
    @Test
    @Transactional
    void getRentByUserId() throws Exception {
        dbManagementTools.createData();
        setup();
        Rent rent1 = rentManager.findAllRents().get(0);
        String clientID = rent1.getUserId();
        mockMvc.perform(get("/rents/getbyuserid/" +clientID ).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userId").value(rent1.getUserId()))
                .andExpect(jsonPath("$[0].virtualDeviceId").value(rent1.getVirtualDeviceId()));
//                .andExpect(jsonPath("$[0].startLocalDateTime").value(rent1.getStartLocalDateTime().toString()))
//                .andExpect(jsonPath("$[0].endLocalDateTime").value(rent1.getEndLocalDateTime().toString()))
        mockMvc.perform(get("/rents/getbyuserid/" + "test321").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void createRentTest() throws Exception {
        dbManagementTools.createData();
        setup();
        int rentSizeBuffer = rentManager.findAllRents().size();
        Rent rent = new Rent();
        var users = userManager.findAllUsers();
        var virtualDevices = virtualDeviceManager.findAllVirtualDevices();
        rent.setUserId(users.get(1).getId());
        rent.setVirtualDeviceId(virtualDevices.get(2).getId());
        rent.setStartLocalDateTime(LocalDateTime.now());
        rent.setEndLocalDateTime(LocalDateTime.now().plusDays(1));
        mockMvc.perform(post("/rents").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
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
        mockMvc.perform(post("/rents").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rent2)))
                .andExpect(status().isBadRequest());
        Rent rent3 = new Rent();
        rent3.setUserId(users.get(0).getId());
        rent3.setVirtualDeviceId(virtualDevices.get(0).getId());
        rent3.setStartLocalDateTime(LocalDateTime.now().plusWeeks(2));
        rent3.setEndLocalDateTime(LocalDateTime.now().plusWeeks(1));
        mockMvc.perform(post("/rents").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rent3)))
                .andExpect(status().isBadRequest());
        Rent rent4 = new Rent();
        rent4.setUserId("NonExistent");
        rent4.setVirtualDeviceId(virtualDevices.get(0).getId());
        rent4.setStartLocalDateTime(LocalDateTime.now().plusWeeks(2));
        rent4.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3));
        mockMvc.perform(post("/rents").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rent4)))
                .andExpect(status().isNotFound());
        Rent rent5 = new Rent();
        rent5.setUserId(users.get(2).getId());
        rent5.setVirtualDeviceId("NonExistent");
        rent5.setStartLocalDateTime(LocalDateTime.now().plusWeeks(2));
        rent5.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3));
        mockMvc.perform(post("/rents").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rent5)))
                .andExpect(status().isNotFound());
        Assertions.assertTrue(rentSizeBuffer + 1== rentManager.findAllRents().size());
    }
    @Test
    @Transactional
    void updateRentTest() throws Exception {
        dbManagementTools.createData();
        setup();
        Rent rent = rentManager.findAllRents().get(0);
        UpdateRentDTO updateRentDTO = new UpdateRentDTO(LocalDateTime.now().plusWeeks(2),LocalDateTime.now().plusWeeks(3));
        mockMvc.perform(put("/rents/" + rent.getRentId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(rent.getUserId()))
                .andExpect(jsonPath("$.virtualDeviceId").value(rent.getVirtualDeviceId()));
        mockMvc.perform(put("/rents/" + rent.getRentId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
        Rent rent2 = rentManager.findAllRents().get(0);
        UpdateRentDTO updateRentDTO2 = new UpdateRentDTO(LocalDateTime.now().minusWeeks(2),LocalDateTime.now().plusWeeks(1));

        Rent rent3 = rentManager.findAllRents().get(0);
        mockMvc.perform(put("/rents/" + rent3.getRentId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rent3)))
                .andExpect(status().isOk());
        mockMvc.perform(put("/rents/" + "NonExistent").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rent3)))
                .andExpect(status().isNotFound());
    }
    @Test
    @Transactional
    void deleteRentTest() throws Exception {
        dbManagementTools.createData();
        setup();
        int rentSizeBuffer = rentManager.findAllRents().size();
        Rent rent = rentManager.findAllRents().get(0);
        Rent rent2 = rentManager.findAllRents().get(1);
        mockMvc.perform(delete("/rents/" + rent.getRentId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isOk());
        Assertions.assertTrue(rentSizeBuffer - 1 == rentManager.findAllRents().size());
        mockMvc.perform(delete("/rents/" + "NonExistent").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/rents/" + rent.getRentId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/rents/" + rent2.getRentId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.IF_MATCH, etag))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(rentSizeBuffer - 1 == rentManager.findAllRents().size());

    }

}

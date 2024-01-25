//package com.example.vmrentalrest.restApiTests;
//
//import com.example.vmrentalrest.DBManagementTools;
//import com.example.vmrentalrest.dto.createuserdto.CreateAdministratorDTO;
//import com.example.vmrentalrest.dto.createuserdto.CreateClientDTO;
//import com.example.vmrentalrest.dto.createuserdto.CreateResourceManagerDTO;
//import com.example.vmrentalrest.dto.getuserdto.GetUserDTO;
//import com.example.vmrentalrest.dto.updatedto.UpdateUserDTO;
//import com.example.vmrentalrest.endpoints.UserEndpoint;
//import com.example.vmrentalrest.managers.UserManager;
//import com.example.vmrentalrest.model.enums.ClientType;
//import com.example.vmrentalrest.model.enums.UserType;
//import com.example.vmrentalrest.model.users.Address;
//import com.example.vmrentalrest.model.users.Client;
//import com.example.vmrentalrest.model.users.User;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//public class userEndpointTest {
//    @Autowired
//    DBManagementTools dbManagementTools;
//    @Autowired
//    UserEndpoint userEndpoint;
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    UserManager userManager;
//    @Autowired
//    ObjectMapper objectMapper;
//    @Test
//    @Transactional
//    void GetAllUsersTest() throws Exception {
//
//        dbManagementTools.createData();
//        var users = userManager.findAllUsers();
//        User user1 = users.get(0);
//        User user2 = users.get(1);
//        User user3 = users.get(2);
//        User user4 = users.get(3);
//        User user5 = users.get(4);
//
//        mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].username").value(user1.getUsername()))
//                .andExpect(jsonPath("$[0].firstName").value(user1.getFirstName()))
//                .andExpect(jsonPath("$[0].lastName").value(user1.getLastName()))
//                .andExpect(jsonPath("$[0].address.city").value(user1.getAddress().getCity()))
//                .andExpect(jsonPath("$[0].address.street").value(user1.getAddress().getStreet()))
//                .andExpect(jsonPath("$[0].address.houseNumber").value(user1.getAddress().getHouseNumber()))
//                .andExpect(jsonPath("$[0].active").value(user1.isActive()))
//                .andExpect(jsonPath("$[0].clientType").value(((Client) user1).getClientType().toString()))
//                .andExpect(jsonPath("$[1].username").value(user2.getUsername()))
//                .andExpect(jsonPath("$[1].firstName").value(user2.getFirstName()))
//                .andExpect(jsonPath("$[1].lastName").value(user2.getLastName()))
//                .andExpect(jsonPath("$[1].address.city").value(user2.getAddress().getCity()))
//                .andExpect(jsonPath("$[1].address.street").value(user2.getAddress().getStreet()))
//                .andExpect(jsonPath("$[1].address.houseNumber").value(user2.getAddress().getHouseNumber()))
//                .andExpect(jsonPath("$[1].active").value(user2.isActive()))
//                .andExpect(jsonPath("$[1].clientType").value(((Client) user2).getClientType().toString()))
//                .andExpect(jsonPath("$[2].username").value(user3.getUsername()))
//                .andExpect(jsonPath("$[2].firstName").value(user3.getFirstName()))
//                .andExpect(jsonPath("$[2].lastName").value(user3.getLastName()))
//                .andExpect(jsonPath("$[2].address.city").value(user3.getAddress().getCity()))
//                .andExpect(jsonPath("$[2].address.street").value(user3.getAddress().getStreet()))
//                .andExpect(jsonPath("$[2].address.houseNumber").value(user3.getAddress().getHouseNumber()))
//                .andExpect(jsonPath("$[2].active").value(user3.isActive()))
//                .andExpect(jsonPath("$[2].clientType").value(((Client) user3).getClientType().toString()))
//                .andExpect(jsonPath("$[3].username").value(user4.getUsername()))
//                .andExpect(jsonPath("$[3].firstName").value(user4.getFirstName()))
//                .andExpect(jsonPath("$[3].lastName").value(user4.getLastName()))
//                .andExpect(jsonPath("$[3].address.city").value(user4.getAddress().getCity()))
//                .andExpect(jsonPath("$[3].address.street").value(user4.getAddress().getStreet()))
//                .andExpect(jsonPath("$[3].address.houseNumber").value(user4.getAddress().getHouseNumber()))
//                .andExpect(jsonPath("$[3].active").value(user4.isActive()))
//                .andExpect(jsonPath("$[4].username").value(user5.getUsername()))
//                .andExpect(jsonPath("$[4].firstName").value(user5.getFirstName()))
//                .andExpect(jsonPath("$[4].lastName").value(user5.getLastName()))
//                .andExpect(jsonPath("$[4].address.city").value(user5.getAddress().getCity()))
//                .andExpect(jsonPath("$[4].address.street").value(user5.getAddress().getStreet()))
//                .andExpect(jsonPath("$[4].address.houseNumber").value(user5.getAddress().getHouseNumber()))
//                .andExpect(jsonPath("$[4].active").value(user5.isActive()));
//
//    }
//    @Test
//    @Transactional
//    void getUserByIdTest() throws Exception {
//        dbManagementTools.createData();
//        Client client = (Client) userManager.findAllUsers().get(0);
//        String id = client.getId();
//        mockMvc.perform(get("/users/{id}",id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(client.getUsername()))
//                .andExpect(jsonPath("$.firstName").value(client.getFirstName()))
//                .andExpect(jsonPath("$.lastName").value(client.getLastName()))
//                .andExpect(jsonPath("$.address.city").value(client.getAddress().getCity()))
//                .andExpect(jsonPath("$.address.street").value(client.getAddress().getStreet()))
//                .andExpect(jsonPath("$.address.houseNumber").value(client.getAddress().getHouseNumber()))
//                .andExpect(jsonPath("$.active").value(client.isActive()))
//                .andExpect(jsonPath("$.clientType").value(client.getClientType().toString()));
//        mockMvc.perform(get("/users/{id}","123"))
//                .andExpect(status().isNotFound());
//
//    }
//    @Test
//    @Transactional
//    void getByUsernameContainsTest() throws Exception {
//        dbManagementTools.createData();
//        var users = userManager.findAllUsers();
//        mockMvc.perform(get("/users/getbyusernamecontains/na"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
//                .andExpect(jsonPath("$[0].firstName").value(users.get(0).getFirstName()))
//                .andExpect(jsonPath("$[0].lastName").value(users.get(0).getLastName()))
//                .andExpect(jsonPath("$[0].address.city").value(users.get(0).getAddress().getCity()))
//                .andExpect(jsonPath("$[0].address.street").value(users.get(0).getAddress().getStreet()))
//                .andExpect(jsonPath("$[0].address.houseNumber").value(users.get(0).getAddress().getHouseNumber()))
//                .andExpect(jsonPath("$[0].active").value(users.get(0).isActive()))
//                .andExpect(jsonPath("$[0].clientType").value(((Client) users.get(0)).getClientType().toString()))
//                .andExpect(jsonPath("$[1].username").value(users.get(4).getUsername()))
//                .andExpect(jsonPath("$[1].firstName").value(users.get(4).getFirstName()))
//                .andExpect(jsonPath("$[1].lastName").value(users.get(4).getLastName()))
//                .andExpect(jsonPath("$[1].address.city").value(users.get(4).getAddress().getCity()))
//                .andExpect(jsonPath("$[1].address.street").value(users.get(4).getAddress().getStreet()))
//                .andExpect(jsonPath("$[1].address.houseNumber").value(users.get(4).getAddress().getHouseNumber()))
//                .andExpect(jsonPath("$[1].active").value(users.get(4).isActive()));
//
//    }
//    @Test
//    @Transactional
//    void getByUsername() throws Exception{
//        dbManagementTools.createData();
//        Client client = (Client) userManager.findAllUsers().get(0);
//        String username = client.getUsername();
//        mockMvc.perform(get("/users/getbyusername")
//                .param("username",username))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(client.getUsername()))
//                .andExpect(jsonPath("$.firstName").value(client.getFirstName()))
//                .andExpect(jsonPath("$.lastName").value(client.getLastName()))
//                .andExpect(jsonPath("$.address.city").value(client.getAddress().getCity()))
//                .andExpect(jsonPath("$.address.street").value(client.getAddress().getStreet()))
//                .andExpect(jsonPath("$.address.houseNumber").value(client.getAddress().getHouseNumber()))
//                .andExpect(jsonPath("$.active").value(client.isActive()))
//                .andExpect(jsonPath("$.clientType").value(client.getClientType().toString()));
//        mockMvc.perform(get("/users/getbyusername")
//                .param("username","test321321"))
//                .andExpect(status().isNotFound());
//    }
//    @Test
//    @Transactional
//    void createUserTest() throws Exception {
//        dbManagementTools.createData();
//        Address address = new Address();
//        address.setCity("Lodz");
//        address.setStreet("Aleje Politechniki");
//        address.setHouseNumber("6/7");
//        CreateClientDTO userClient = new CreateClientDTO();
//        userClient.setUsername("test");
//        userClient.setFirstName("test");
//        userClient.setLastName("test");
//        userClient.setAddress(address);
//        userClient.setClientType(ClientType.SILVER);
//
//        String json = objectMapper.writeValueAsString(userClient);
//        mockMvc.perform(post("/users/createclient")
//                .content(json)
//                .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(userClient.getUsername()))
//                .andExpect(jsonPath("$.firstName").value(userClient.getFirstName()))
//                .andExpect(jsonPath("$.lastName").value(userClient.getLastName()))
//                .andExpect(jsonPath("$.address.city").value(userClient.getAddress().getCity()))
//                .andExpect(jsonPath("$.address.street").value(userClient.getAddress().getStreet()))
//                .andExpect(jsonPath("$.address.houseNumber").value(userClient.getAddress().getHouseNumber()))
//                .andExpect(jsonPath("$.clientType").value(userClient.getClientType().toString()));
//        mockMvc.perform(post("/users/createclient")
//                .content(json)
//                .contentType("application/json"))
//                .andExpect(status().isBadRequest());
//        Address address2 = new Address();
//        address2.setCity("Olsztyn");
//        address2.setStreet("Narutowicza");
//        address2.setHouseNumber("7");
//        CreateAdministratorDTO userAdministrator = new CreateAdministratorDTO();
//        userAdministrator.setUsername("test2");
//        userAdministrator.setFirstName("test2");
//        userAdministrator.setLastName("test2");
//        userAdministrator.setAddress(address2);
//        String json2 = objectMapper.writeValueAsString(userAdministrator);
//        mockMvc.perform(post("/users/createadministrator")
//                .content(json2)
//                .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(userAdministrator.getUsername()))
//                .andExpect(jsonPath("$.firstName").value(userAdministrator.getFirstName()))
//                .andExpect(jsonPath("$.lastName").value(userAdministrator.getLastName()))
//                .andExpect(jsonPath("$.address.city").value(userAdministrator.getAddress().getCity()))
//                .andExpect(jsonPath("$.address.street").value(userAdministrator.getAddress().getStreet()))
//                .andExpect(jsonPath("$.address.houseNumber").value(userAdministrator.getAddress().getHouseNumber()))
//                .andExpect(jsonPath("$.clientType").doesNotExist());
//        Address address3 = new Address();
//        address3.setCity("Warszawa");
//        address3.setStreet("Marszalkowska");
//        address3.setHouseNumber("1");
//        CreateResourceManagerDTO userResourceManager = new CreateResourceManagerDTO();
//        userResourceManager.setUsername("test2");
//        userResourceManager.setFirstName("test2");
//        userResourceManager.setLastName("test2");
//        userResourceManager.setAddress(address3);
//        String json3 = objectMapper.writeValueAsString(userResourceManager);
//        mockMvc.perform(post("/users/createresourcemanager")
//                .content(json3)
//                .contentType("application/json"))
//                .andExpect(status().isBadRequest());
//        userResourceManager.setLastName(null);
//        String json4 = objectMapper.writeValueAsString(userResourceManager);
//        mockMvc.perform(post("/users/createresourcemanager")
//                .content(json4)
//                .contentType("application/json"))
//                .andExpect(status().isBadRequest());
//    }
//    @Test
//    @Transactional
//    void updateUserTest() throws Exception {
//        dbManagementTools.createData();
//        Client client = (Client) userManager.findAllUsers().get(0);
//        UpdateUserDTO userClient = new UpdateUserDTO("test212121","test77777",null,null,null);
//        String json = objectMapper.writeValueAsString(userClient);
//        mockMvc.perform(put("/users/{id}",client.getId())
//                .content(json)
//                .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(client.getUsername()))
//                .andExpect(jsonPath("$.firstName").value(userClient.firstName()))
//                .andExpect(jsonPath("$.lastName").value(userClient.lastName()))
//                .andExpect(jsonPath("$.address.city").value(client.getAddress().getCity()))
//                .andExpect(jsonPath("$.address.street").value(client.getAddress().getStreet()))
//                .andExpect(jsonPath("$.address.houseNumber").value(client.getAddress().getHouseNumber()));
//        mockMvc.perform(put("/users/{id}","123")
//                .content(json)
//                .contentType("application/json"))
//                .andExpect(status().isNotFound());
//        UpdateUserDTO userClient2 = new UpdateUserDTO("te","t",null,null,null);
//        String json2 = objectMapper.writeValueAsString(userClient2);
//        mockMvc.perform(put("/users/{id}",client.getId())
//                .content(json2)
//                .contentType("application/json"))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @Transactional
//    void activateAndDeactivateUserTest() throws Exception {
//        dbManagementTools.createData();
//        Client client = (Client) userManager.findAllUsers().get(0);
//        Assertions.assertTrue(client.isActive());
//        mockMvc.perform(patch("/users/{id}/deactivate",client.getId()))
//                .andExpect(status().isOk());
//        client = (Client) userManager.findAllUsers().get(0);
//        Assertions.assertFalse(client.isActive());
//        mockMvc.perform(patch("/users/{id}/activate",client.getId()))
//                .andExpect(status().isOk());
//        client = (Client) userManager.findAllUsers().get(0);
//        Assertions.assertTrue(client.isActive());
//        mockMvc.perform(patch("/users/{id}/deactivate","123"))
//                .andExpect(status().isNotFound());
//        mockMvc.perform(patch("/users/{id}/activate","123"))
//                .andExpect(status().isNotFound());
//    }
//
//}

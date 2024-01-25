package com.example.vmrentalrest.managerTests;

import com.example.vmrentalrest.DBManagementTools;
import com.example.vmrentalrest.dto.updatedto.UpdateUserDTO;
import com.example.vmrentalrest.exceptions.IllegalOperationException;
import com.example.vmrentalrest.exceptions.RecordNotFoundException;
import com.example.vmrentalrest.managers.UserManager;
import com.example.vmrentalrest.model.enums.ClientType;
import com.example.vmrentalrest.model.users.Address;
import com.example.vmrentalrest.model.users.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class UserManagerTest {
    @Autowired
    UserManager userManager;
    @Autowired
    DBManagementTools dbManagementTools;

    private void addUsers() {
        dbManagementTools.createData();
    }

    private void createMPClient() {
        Client client1 = new Client();
        Address address1 = new Address();
        address1.setCity("Lodz");
        address1.setStreet("Aleje Politechniki");
        address1.setHouseNumber("4/7");
        client1.setUsername("Pudzianator");
        client1.setFirstName("Mariusz");
        client1.setLastName("Pudzianowski");
        client1.setAddress(address1);
        client1.setClientType(ClientType.GOLD);
        userManager.createClient(client1);
    }

    @Test
    @Transactional
    void duplicateRecordExceptionTest() {
        addUsers();
        Assertions.assertThatThrownBy(() -> createMPClient()).isInstanceOf(IllegalOperationException.class);
    }


    @Test
    @Transactional
    void addUserTest() {
        int buffer = userManager.findAllUsers().size();
        addUsers();
        Assertions.assertThat(userManager.findAllUsers().size() == buffer + 5).isTrue();
    }


    @Test
    @Transactional
    void findUserTest() {
        addUsers();
        var idBuffer = userManager.findAllUsers().get(0).getId();
        Assertions.assertThat(idBuffer.equals(userManager.findAllUsers().get(0).getId())).isTrue();
    }

    @Test
    @Transactional
    void updateUserTest() {
        addUsers();
        String idBuffer = userManager.findAllUsers().get(1).getId();
        Address address = new Address();
        address.setCity("Warszawa");
        address.setStreet("Nowy Swiat");
        address.setHouseNumber("74");
        UpdateUserDTO client = new UpdateUserDTO(null, "Małysz2", null, null, address);
        userManager.updateUser(idBuffer, client);
        userManager.updateClientType(idBuffer, ClientType.SILVER);
        Assertions.assertThat(userManager.findUserById(idBuffer).getFirstName().equals("Adam")).isTrue();
        Assertions.assertThat(userManager.findUserById(idBuffer).getLastName().equals("Małysz2")).isTrue();
        Assertions.assertThat(userManager.findUserById(idBuffer).getAddress().getCity().equals("Warszawa")).isTrue();
        Assertions.assertThat(userManager.findUserById(idBuffer).getAddress().getStreet().equals("Nowy Swiat")).isTrue();
        Assertions.assertThat(userManager.findUserById(idBuffer).getAddress().getHouseNumber().equals("74")).isTrue();
        Assertions.assertThat(((Client) userManager.findUserById(idBuffer)).getClientType().equals(ClientType.SILVER)).isTrue();
    }

    @Test
    @Transactional
    void setActiveTest() {
        addUsers();
        String idBuffer = userManager.findAllUsers().get(3).getId();
        userManager.setInactive(idBuffer);
        Assertions.assertThat(userManager.findUserById(idBuffer).isActive()).isFalse();
        userManager.setActive(idBuffer);
        Assertions.assertThat(userManager.findUserById(idBuffer).isActive()).isTrue();
    }
    @Test
    @Transactional
    void setInactiveTest() {
        addUsers();
        String idBuffer = userManager.findAllUsers().get(0).getId();
        Assertions.assertThat(userManager.findUserById(idBuffer).isActive()).isTrue();
        userManager.setInactive(idBuffer);
        Assertions.assertThat(userManager.findUserById(idBuffer).isActive()).isFalse();
    }
    @Test
    @Transactional
    void findByUsernameTest() {
        addUsers();
        Assertions.assertThat(userManager.findByUsername("Pudzianator").getFirstName().equals("Mariusz")).isTrue();
        Assertions.assertThat(userManager.findByUsername("Pudzianator").getLastName().equals("Pudzianowski")).isTrue();
        Assertions.assertThat(userManager.findByUsername("Pudzianator").getAddress().getCity().equals("Lodz")).isTrue();
        Assertions.assertThat(userManager.findByUsername("Pudzianator").getAddress().getStreet().equals("Aleje Politechniki")).isTrue();
        Assertions.assertThat(userManager.findByUsername("Pudzianator").getAddress().getHouseNumber().equals("4/7")).isTrue();
        Assertions.assertThat(((Client) userManager.findByUsername("Pudzianator")).getClientType().equals(ClientType.BRONZE)).isTrue();
        Assertions.assertThatThrownBy(() -> userManager.findByUsername("notFound")).isInstanceOf(RecordNotFoundException.class);
    }
    @Test
    @Transactional
    void findAllByUsernameContainsIgnoreCase() {
        addUsers();
        Assertions.assertThat(userManager.findAllByUsernameContainsIgnoreCase("Na").size() == 2).isTrue();
        Assertions.assertThat(userManager.findAllByUsernameContainsIgnoreCase("notFound").isEmpty()).isTrue();
    }
}
package com.example.nbd;

import com.example.nbd.exceptions.DuplicateRecordException;
import com.example.nbd.exceptions.UnknownUserTypeException;
import com.example.nbd.managers.UserManager;
import com.example.nbd.model.enums.ClientType;
import com.example.nbd.model.enums.UserType;
import com.example.nbd.model.users.Address;
import com.example.nbd.model.users.Administrator;
import com.example.nbd.model.users.Client;
import com.example.nbd.model.users.ResourceManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserManagerTest {
    @Autowired
    UserManager userManager;

    private void addClients() throws DuplicateRecordException, UnknownUserTypeException {
        createMPClient();
        Client client2 = new Client();
        Address address2 = new Address();
        address2.setCity("Warszawa");
        address2.setStreet("Nowy Swiat");
        address2.setHouseNumber("7");
        client2.setFirstName("Adam");
        client2.setLastName("Małysz");
        client2.setAddress(address2);
        client2.setClientType(ClientType.BRONZE);
        userManager.createUser(client2, UserType.CLIENT);
        Administrator administrator = new Administrator();
        Address address3 = new Address();
        address3.setCity("Płock");
        address3.setStreet("Kościuszki");
        address3.setHouseNumber("1");
        administrator.setFirstName("Jan");
        administrator.setLastName("Kowalski");
        administrator.setAddress(address3);
        userManager.createUser(administrator, UserType.ADMINISTRATOR);
        ResourceManager resourceManager = new ResourceManager();
        Address address4 = new Address();
        address4.setCity("Gdynia");
        address4.setStreet("Mickiewicza");
        address4.setHouseNumber("2");
        resourceManager.setFirstName("Andrzej");
        resourceManager.setLastName("Nowak");
        resourceManager.setAddress(address4);
        userManager.createUser(resourceManager, UserType.RESOURCE_MANAGER);
    }

    private void createMPClient() throws DuplicateRecordException, UnknownUserTypeException {
        Client client1 = new Client();
        Address address1 = new Address();
        address1.setCity("Lodz");
        address1.setStreet("Aleje Politechniki");
        address1.setHouseNumber("4/7");
        client1.setFirstName("Mariusz");
        client1.setLastName("Pudzianowski");
        client1.setAddress(address1);
        client1.setClientType(ClientType.GOLD);
        userManager.createUser(client1, UserType.CLIENT);
    }

    @Test
    @Transactional
    void cantAddClientWithSameNameAndSurnameAndAddressTest() throws DuplicateRecordException, UnknownUserTypeException {
        addClients();
        try {
            createMPClient();
            Assertions.fail("Exception was not caught");
        } catch (DuplicateRecordException e) {
            Assertions.assertThat(e.getMessage().equals("Record already exists")).isTrue();
        } catch (UnknownUserTypeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    void unknownUserTypeExceptionTest() throws DuplicateRecordException {
        try {
            Client client1 = new Client();
            Address address1 = new Address();
            address1.setCity("Lodz");
            address1.setStreet("Aleje Politechniki");
            address1.setHouseNumber("4/7");
            client1.setFirstName("Mariusz");
            client1.setLastName("Pudzianowski");
            client1.setAddress(address1);
            client1.setClientType(ClientType.GOLD);
            userManager.createUser(client1, null);
            Assertions.fail("Exception was not caught");
        } catch (UnknownUserTypeException e) {
            Assertions.assertThat(e.getMessage().equals("Unknown user type")).isTrue();
        }
    }

    @Test
    @Transactional
    void addClientTest() throws DuplicateRecordException, UnknownUserTypeException {
        int buffer = userManager.findAllUsers().size();
        addClients();
        Assertions.assertThat(userManager.findAllUsers().size() == buffer + 4).isTrue();
    }


    @Test
    @Transactional
    void findClientTest() throws DuplicateRecordException, UnknownUserTypeException {
        addClients();
        var idBuffer = userManager.findAllUsers().get(0).getId();
        Assertions.assertThat(idBuffer.equals(idBuffer)).isTrue();
    }

    @Test
    @Transactional
    void updateUserTest() throws DuplicateRecordException, UnknownUserTypeException {
        addClients();
        String idBuffer = userManager.findAllUsers().get(0).getId();
        Client client = new Client();
        Address address = new Address();
        address.setCity("Warszawa");
        address.setStreet("Nowy Swiat");
        address.setHouseNumber("7");
        client.setFirstName("Adam");
        client.setLastName("Małysz");
        client.setAddress(address);
        client.setClientType(ClientType.BRONZE);
        userManager.updateUser(idBuffer, client);
        Assertions.assertThat(userManager.findUserById(idBuffer).getFirstName().equals("Adam")).isTrue();
        Assertions.assertThat(userManager.findUserById(idBuffer).getLastName().equals("Małysz")).isTrue();
        Assertions.assertThat(userManager.findUserById(idBuffer).getAddress().getCity().equals("Warszawa")).isTrue();
        Assertions.assertThat(userManager.findUserById(idBuffer).getAddress().getStreet().equals("Nowy Swiat")).isTrue();
        Assertions.assertThat(userManager.findUserById(idBuffer).getAddress().getHouseNumber().equals("7")).isTrue();
        Assertions.assertThat(((Client) userManager.findUserById(idBuffer)).getClientType().equals(ClientType.BRONZE)).isTrue();
    }

    @Test
    @Transactional
    void setActiveTest() throws DuplicateRecordException, UnknownUserTypeException {
        addClients();
        String idBuffer = userManager.findAllUsers().get(0).getId();
        userManager.setActive(idBuffer);
        Assertions.assertThat(userManager.findUserById(idBuffer).isActive()).isTrue();
    }

    @Test
    @Transactional
    void setInactiveTest() throws DuplicateRecordException, UnknownUserTypeException {
        addClients();
        String idBuffer = userManager.findAllUsers().get(0).getId();
        userManager.setInactive(idBuffer);
        Assertions.assertThat(userManager.findUserById(idBuffer).isActive()).isFalse();
    }
}
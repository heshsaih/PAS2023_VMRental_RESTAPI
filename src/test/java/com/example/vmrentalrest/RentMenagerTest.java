package com.example.vmrentalrest;

import com.example.vmrentalrest.exceptions.illegalOperationExceptions.*;
import com.example.vmrentalrest.exceptions.invalidParametersExceptions.*;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.RentNotFoundException;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.UserNotFoundException;
import com.example.vmrentalrest.managers.UserManager;
import com.example.vmrentalrest.managers.RentManager;
import com.example.vmrentalrest.managers.VirtualDeviceManager;
import com.example.vmrentalrest.model.Rent;
import com.example.vmrentalrest.model.enums.*;
import com.example.vmrentalrest.model.users.Address;
import com.example.vmrentalrest.model.users.Administrator;
import com.example.vmrentalrest.model.users.Client;
import com.example.vmrentalrest.model.users.ResourceManager;
import com.example.vmrentalrest.model.virtualdevices.VirtualDatabaseServer;
import com.example.vmrentalrest.model.virtualdevices.VirtualMachine;
import com.example.vmrentalrest.model.virtualdevices.VirtualPhone;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class RentMenagerTest {
    @Autowired
    UserManager userManager;
    @Autowired
    VirtualDeviceManager virtualDeviceManager;
    @Autowired
    RentManager rentManager;


    @Test
    void contextLoads() {
    }
    private void createTestingData() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, UnknownUserTypeException, InvalidUserException, InvalidVirtualDeviceException, UserIsNotActiveException, UserNotFoundException, InvalidRentException {
        Client client1 = new Client();
        Address address1 = new Address();
        address1.setCity("Lodz");
        address1.setStreet("Aleje Politechniki");
        address1.setHouseNumber("4/7");
        client1.setUsername("Pudzianator");
        client1.setFirstName("Mariusz");
        client1.setLastName("Pudzianowski");
        client1.setAddress(address1);
        client1.setClientType(ClientType.BRONZE);
        userManager.createUser(client1, UserType.CLIENT);
        Client client2 = new Client();
        Address address2 = new Address();
        address2.setCity("Warszawa");
        address2.setStreet("Nowy Swiat");
        address2.setHouseNumber("7");
        client2.setUsername("małyszo2");
        client2.setFirstName("Adam");
        client2.setLastName("Małysz");
        client2.setAddress(address2);
        client2.setClientType(ClientType.BRONZE);
        userManager.createUser(client2, UserType.CLIENT);
        Client client3 = new Client();
        Address address5 = new Address();
        address5.setCity("Pabianice");
        address5.setStreet("Jana Pawla 2");
        address5.setHouseNumber("21");
        client3.setUsername("kamil");
        client3.setFirstName("Kamil");
        client3.setLastName("Stoch");
        client3.setAddress(address5);
        client3.setClientType(ClientType.GOLD);
        userManager.createUser(client3, UserType.CLIENT);
        Administrator administrator = new Administrator();
        Address address3 = new Address();
        address3.setCity("Płock");
        address3.setStreet("Kościuszki");
        address3.setHouseNumber("1");
        administrator.setUsername("admin");
        administrator.setFirstName("Jan");
        administrator.setLastName("Kowalski");
        administrator.setAddress(address3);
        userManager.createUser(administrator, UserType.ADMINISTRATOR);
        ResourceManager resourceManager = new ResourceManager();
        Address address4 = new Address();
        address4.setCity("Gdynia");
        address4.setStreet("Mickiewicza");
        address4.setHouseNumber("2");
        resourceManager.setUsername("resmanager");
        resourceManager.setFirstName("Andrzej");
        resourceManager.setLastName("Nowak");
        resourceManager.setAddress(address4);
        userManager.createUser(resourceManager, UserType.RESOURCE_MANAGER);
        userManager.findAllUsers().get(3).setActive(false);
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
        Rent rent1 = new Rent();
        rent1.setClientId(userManager.findAllUsers().get(0).getId());
        rent1.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(0).getId());
        rent1.setStartLocalDateTime(LocalDateTime.now().plusDays(1));
        rent1.setEndLocalDateTime(LocalDateTime.now().plusWeeks(2L));
        rentManager.createRent(rent1);
        Rent rent2 = new Rent();
        rent2.setClientId(userManager.findAllUsers().get(1).getId());
        rent2.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(0).getId());
        rent2.setStartLocalDateTime(LocalDateTime.now().minusDays(5));
        rent2.setEndLocalDateTime(LocalDateTime.now().minusDays(1));
        rentManager.createRent(rent2);
    }

    @Test
    @Transactional
    void findAllRentsTest() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, UserIsNotActiveException, UserNotFoundException, UnknownUserTypeException, InvalidVirtualDeviceException, InvalidUserException, RentNotFoundException, InvalidRentException {
        int bufferedRents = rentManager.findAllRents().size();
        createTestingData();
        Assertions.assertThat(rentManager.findAllRents().get(0).equals(rentManager.findRentById(rentManager.findAllRents().get(0).getRentId()))).isTrue();
        Assertions.assertThat(rentManager.findAllRents().size() == bufferedRents + 2).isTrue();
    }
    @Test
    @Transactional
    void rentNotFoundExceptionTest() {
        Assertions.assertThatThrownBy(() -> rentManager.findRentById("123")).isInstanceOf(RentNotFoundException.class);
    }
    @Test
    @Transactional
    void createRentTest() throws DuplicateRecordException, UserIsNotActiveException, UserNotFoundException, InvalidDatesException, UnknownUserTypeException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidVirtualDeviceException, InvalidUserException, InvalidRentException {
        createTestingData();
        Rent rent = new Rent();
        rent.setClientId(userManager.findAllUsers().get(0).getId());
        rent.setStartLocalDateTime(LocalDateTime.now());
        rent.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3L));
        Assertions.assertThatThrownBy(() -> rentManager.createRent(rent)).isInstanceOf(InvalidRentException.class);
        Assertions.assertThatThrownBy(() -> rentManager.createRent(null)).isInstanceOf(InvalidRentException.class);
        userManager.setInactive(userManager.findAllUsers().get(0).getId());
        rent.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(2).getId());
        Assertions.assertThatThrownBy(() -> rentManager.createRent(rent)).isInstanceOf(UserIsNotActiveException.class);
        rent.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(0).getId());
        Client client = (Client) userManager.findUserById(rent.getClientId());
        client.setClientType(ClientType.GOLD);
        userManager.updateUser(client.getId(), client);
        userManager.setActive(userManager.findAllUsers().get(0).getId());
        Assertions.assertThatThrownBy(() -> rentManager.createRent(rent)).isInstanceOf(DeviceAlreadyRentedException.class);
    }

    @Test
    @Transactional
    void clientHasTooManyRentsExceptionTest() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, UserIsNotActiveException, UserNotFoundException, UnknownUserTypeException, InvalidVirtualDeviceException, InvalidUserException, InvalidRentException {
        createTestingData();
        try {
            Rent rent = new Rent();
            rent.setClientId(userManager.findAllUsers().get(0).getId());
            rent.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(0).getId());
            rent.setStartLocalDateTime(LocalDateTime.now());
            rent.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3L));
            rentManager.createRent(rent);
            Assertions.fail("Exception has not been thrown");
        } catch (ClientHasTooManyRentsException e) {
            Assertions.assertThat(e.getMessage().equals("Client has too many rents")).isTrue();
        } catch (DeviceAlreadyRentedException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @Transactional
    void updateRentTest() throws DuplicateRecordException, UserIsNotActiveException, UserNotFoundException, InvalidRentException, InvalidDatesException, UnknownUserTypeException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidVirtualDeviceException, InvalidUserException, RentNotFoundException, CantUpdateRentException {
        createTestingData();
        Assertions.assertThatThrownBy(() -> rentManager.updateRent("123",null)).isInstanceOf(InvalidRentException.class);
        Rent rent = rentManager.findAllRents().get(0);
        rent.setStartLocalDateTime(LocalDateTime.now().plusDays(1));
        rent.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3L));
        rentManager.updateRent(rent.getRentId(),rent);
        Assertions.assertThat(rentManager.findRentById(rent.getRentId()).getStartLocalDateTime()
                .equals(rent.getStartLocalDateTime().truncatedTo(ChronoUnit.MILLIS))).isTrue();
        Assertions.assertThat(rentManager.findRentById(rent.getRentId()).getEndLocalDateTime()
                .equals(rent.getEndLocalDateTime().truncatedTo(ChronoUnit.MILLIS))).isTrue();
        rent.setStartLocalDateTime(LocalDateTime.now().minusDays(1));
        rent.setEndLocalDateTime(LocalDateTime.now().minusWeeks(3L));
        Assertions.assertThatThrownBy(() -> rentManager.updateRent(rent.getRentId(),rent)).isInstanceOf(InvalidDatesException.class);
        Rent rent1 = new Rent();
        rent1.setStartLocalDateTime(LocalDateTime.now().minusWeeks(2L));
        rent1.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3L));
        Assertions.assertThatThrownBy(() -> rentManager.updateRent(rentManager.findAllRents().get(0).getRentId(),rent1))
                .isInstanceOf(CantUpdateRentException.class);


    }
    @Test
    @Transactional
    public void deleteRentTest() throws DuplicateRecordException, InvalidDatesException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, UserIsNotActiveException, UserNotFoundException, UnknownUserTypeException, InvalidVirtualDeviceException, InvalidUserException, CantDeleteRentException, RentNotFoundException, InvalidRentException {
        createTestingData();
        String bufferedRentId = rentManager.findAllRents().get(0).getRentId();
        int bufferedRents = rentManager.findAllRents().size();
        var rent = rentManager.findRentById(bufferedRentId);
        int ActiveRentsSize = ((Client) userManager.findUserById(rent.getClientId())).getActiveRents().size();
        rentManager.deleteRent(bufferedRentId);
        Assertions.assertThat(rentManager.findAllRents().size() == bufferedRents - 1).isTrue();
        Assertions.assertThat(((Client) userManager.findUserById(rent.getClientId())).getActiveRents().size() == ActiveRentsSize - 1).isTrue();
        Assertions.assertThatThrownBy(() -> rentManager.findRentById(bufferedRentId)).isInstanceOf(RentNotFoundException.class);

    }
}

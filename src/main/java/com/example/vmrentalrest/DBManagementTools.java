package com.example.vmrentalrest;

import com.example.vmrentalrest.managers.RentManager;
import com.example.vmrentalrest.managers.UserManager;
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
import com.example.vmrentalrest.repositories.RentRepository;
import com.example.vmrentalrest.repositories.UserRepository;
import com.example.vmrentalrest.repositories.VirtualDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DBManagementTools {
    private final UserRepository userRepository;
    private final RentRepository rentRepository;
    private final VirtualDeviceRepository virtualDeviceRepository;
    private final UserManager userManager;
    private final RentManager rentManager;
    private final VirtualDeviceManager virtualDeviceManager;
    public void clearRecords() {
        userRepository.deleteAll();
        rentRepository.deleteAll();
        virtualDeviceRepository.deleteAll();
    }
    public void createData() {
        Client client1 = new Client();
        Address address1 = new Address();
        address1.setCity("Lodz");
        address1.setStreet("Aleje Politechniki");
        address1.setHouseNumber("4/7");
        client1.setUsername("Pudzianator");
        client1.setFirstName("Mariusz");
        client1.setActive(true);
        client1.setEmail("pudzian@gmail.com");
        client1.setPassword("1234");
        client1.setLastName("Pudzianowski");
        client1.setAddress(address1);
        client1.setClientType(ClientType.BRONZE);
        userManager.createClient(client1);
        Client client2 = new Client();
        Address address2 = new Address();
        address2.setCity("Warszawa");
        address2.setStreet("Nowy Swiat");
        address2.setHouseNumber("7");
        client2.setUsername("małyszo2");
        client2.setEmail("malys@gmail.com");
        client2.setFirstName("Adam");
        client2.setActive(true);
        client2.setLastName("Małysz");
        client2.setPassword("12343232");
        client2.setAddress(address2);
        client2.setClientType(ClientType.BRONZE);
        userManager.createClient(client2);
        Client client3 = new Client();
        Address address5 = new Address();
        address5.setCity("Pabianice");
        address5.setStreet("Jana Pawla 2");
        address5.setHouseNumber("21");
        client3.setUsername("kamil");
        client3.setFirstName("Kamil");
        client3.setLastName("Stoch");
        client3.setActive(true);
        client3.setEmail("stoch@gmail.com");
        client3.setPassword("haslo123");
        client3.setAddress(address5);
        client3.setClientType(ClientType.GOLD);
        userManager.createClient(client3);
        Administrator administrator = new Administrator();
        Address address3 = new Address();
        address3.setCity("Płock");
        address3.setStreet("Kościuszki");
        address3.setHouseNumber("1");
        administrator.setUsername("admin");
        administrator.setFirstName("Jan");
        administrator.setActive(true);
        administrator.setEmail("admin@gmail.com)");
        administrator.setLastName("Kowalski");
        administrator.setPassword("admin123");
        administrator.setAddress(address3);
        userManager.createAdministrator(administrator);
        ResourceManager resourceManager = new ResourceManager();
        Address address4 = new Address();
        address4.setCity("Gdynia");
        address4.setStreet("Mickiewicza");
        address4.setHouseNumber("2");
        resourceManager.setUsername("resmanager");
        resourceManager.setFirstName("Andrzej");
        resourceManager.setLastName("Nowak");
        resourceManager.setActive(true);
        resourceManager.setEmail("resmen@gmail.com");
        resourceManager.setPassword("resmanager123");
        resourceManager.setAddress(address4);
        userManager.createResourceManager(resourceManager);
        userManager.findAllUsers().get(3).setActive(false);
        VirtualDatabaseServer virtualDatabaseServer = new VirtualDatabaseServer();
        virtualDatabaseServer.setCpuCores(16);
        virtualDatabaseServer.setRam(64);
        virtualDatabaseServer.setStorageSize(2048);
        virtualDatabaseServer.setDatabaseType(DatabaseType.MONGODB);
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
        rent1.setUserId(userManager.findAllUsers().get(0).getId());
        rent1.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(0).getId());
        rent1.setStartLocalDateTime(LocalDateTime.now().plusDays(1));
        rent1.setEndLocalDateTime(LocalDateTime.now().plusWeeks(2L));
        rentManager.createRent(rent1);
        Rent rent2 = new Rent();
        rent2.setUserId(userManager.findAllUsers().get(1).getId());
        rent2.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(0).getId());
        rent2.setStartLocalDateTime(LocalDateTime.now().minusDays(5));
        rent2.setEndLocalDateTime(LocalDateTime.now().minusDays(1));
        rentManager.createRent(rent2);
        userManager.findAllUsers().get(3).setActive(false);
    }
}

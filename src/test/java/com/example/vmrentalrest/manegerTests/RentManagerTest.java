package com.example.vmrentalrest.manegerTests;

import com.example.vmrentalrest.DBManagementTools;
import com.example.vmrentalrest.exceptions.illegalOperationExceptions.*;
import com.example.vmrentalrest.exceptions.invalidParametersExceptions.*;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.RentNotFoundException;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.UserNotFoundException;
import com.example.vmrentalrest.managers.UserManager;
import com.example.vmrentalrest.managers.RentManager;
import com.example.vmrentalrest.managers.VirtualDeviceManager;
import com.example.vmrentalrest.model.Rent;
import com.example.vmrentalrest.model.enums.*;
import com.example.vmrentalrest.model.users.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
@ActiveProfiles("test")
class RentManagerTest {
    @Autowired
    DBManagementTools dbManagementTools;
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
       dbManagementTools.createData();
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
        rent.setUserId(userManager.findAllUsers().get(0).getId());
        rent.setStartLocalDateTime(LocalDateTime.now());
        rent.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3L));
        Assertions.assertThatThrownBy(() -> rentManager.createRent(rent)).isInstanceOf(InvalidRentException.class);
        Assertions.assertThatThrownBy(() -> rentManager.createRent(null)).isInstanceOf(InvalidRentException.class);
        userManager.setInactive(userManager.findAllUsers().get(0).getId());
        rent.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(2).getId());
        Assertions.assertThatThrownBy(() -> rentManager.createRent(rent)).isInstanceOf(UserIsNotActiveException.class);
        rent.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(0).getId());
        Client client = (Client) userManager.findUserById(rent.getUserId());
        client.setClientType(ClientType.GOLD);
        userManager.updateUser(client.getId(), client,UserType.CLIENT);
        userManager.setActive(userManager.findAllUsers().get(0).getId());
        Assertions.assertThatThrownBy(() -> rentManager.createRent(rent)).isInstanceOf(DeviceAlreadyRentedException.class);
    }

    @Test
    @Transactional
    void clientHasTooManyRentsExceptionTest() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, UserIsNotActiveException, UserNotFoundException, UnknownUserTypeException, InvalidVirtualDeviceException, InvalidUserException, InvalidRentException {
        createTestingData();
        try {
            Rent rent = new Rent();
            rent.setUserId(userManager.findAllUsers().get(0).getId());
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
        int ActiveRentsSize = ((Client) userManager.findUserById(rent.getUserId())).getActiveRents().size();
        rentManager.deleteRent(bufferedRentId);
        Assertions.assertThat(rentManager.findAllRents().size() == bufferedRents - 1).isTrue();
        Assertions.assertThat(((Client) userManager.findUserById(rent.getUserId())).getActiveRents().size() == ActiveRentsSize - 1).isTrue();
        Assertions.assertThatThrownBy(() -> rentManager.findRentById(bufferedRentId)).isInstanceOf(RentNotFoundException.class);

    }
}

package com.example.vmrentalrest.managerTests;

import com.example.vmrentalrest.DBManagementTools;
import com.example.vmrentalrest.dto.updatedto.UpdateRentDTO;
import com.example.vmrentalrest.exceptions.IllegalOperationException;
import com.example.vmrentalrest.exceptions.RecordNotFoundException;
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
    private void createTestingData() {
       dbManagementTools.createData();
    }

    @Test
    @Transactional
    void findAllRentsTest() {
        int bufferedRents = rentManager.findAllRents().size();
        createTestingData();
        Assertions.assertThat(rentManager.findAllRents().get(0).equals(rentManager.findRentById(rentManager.findAllRents().get(0).getRentId()))).isTrue();
        Assertions.assertThat(rentManager.findAllRents().size() == bufferedRents + 2).isTrue();
    }
    @Test
    @Transactional
    void rentNotFoundExceptionTest() {
        Assertions.assertThatThrownBy(() -> rentManager.findRentById("123")).isInstanceOf(RecordNotFoundException.class);
    }
    @Test
    @Transactional
    void createRentTest() {
        createTestingData();
        Rent rent = new Rent();
        rent.setUserId(userManager.findAllUsers().get(0).getId());
        rent.setStartLocalDateTime(LocalDateTime.now());
        rent.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3L));
        Assertions.assertThatThrownBy(() -> rentManager.createRent(rent)).isInstanceOf(IllegalOperationException.class);
        Assertions.assertThatThrownBy(() -> rentManager.createRent(null)).isInstanceOf(IllegalOperationException.class);
        userManager.setInactive(userManager.findAllUsers().get(0).getId());
        rent.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(2).getId());
        Assertions.assertThatThrownBy(() -> rentManager.createRent(rent)).isInstanceOf(IllegalOperationException.class);
        rent.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(0).getId());
        Client client = (Client) userManager.findUserById(rent.getUserId());
        userManager.updateClientType(client.getId(), ClientType.GOLD);
        userManager.setActive(userManager.findAllUsers().get(0).getId());
        Assertions.assertThatThrownBy(() -> rentManager.createRent(rent)).isInstanceOf(IllegalOperationException.class);
    }

    @Test
    @Transactional
    void clientHasTooManyRentsExceptionTest() {
        createTestingData();
        Rent rent = new Rent();
        rent.setUserId(userManager.findAllUsers().get(0).getId());
        rent.setVirtualDeviceId(virtualDeviceManager.findAllVirtualDevices().get(0).getId());
        rent.setStartLocalDateTime(LocalDateTime.now());
        rent.setEndLocalDateTime(LocalDateTime.now().plusWeeks(3L));
        Assertions.assertThatThrownBy(() -> rentManager.createRent(rent)).isInstanceOf(IllegalOperationException.class);
    }
    @Test
    @Transactional
    void updateRentTest() {
        createTestingData();
        Assertions.assertThatThrownBy(() -> rentManager.updateRent("123",null)).isInstanceOf(IllegalOperationException.class);
        Rent rent = rentManager.findAllRents().get(0);
        UpdateRentDTO updateRentDTO = new UpdateRentDTO(LocalDateTime.now().plusDays(1),LocalDateTime.now().plusWeeks(3L));
        rentManager.updateRent(rent.getRentId(),updateRentDTO);
        Rent rent1 = rentManager.findAllRents().get(0);
        Assertions.assertThat(rentManager.findRentById(rent1.getRentId()).getStartLocalDateTime()
                .equals(rent1.getStartLocalDateTime().truncatedTo(ChronoUnit.MILLIS))).isTrue();
        Assertions.assertThat(rentManager.findRentById(rent1.getRentId()).getEndLocalDateTime()
                .equals(rent1.getEndLocalDateTime().truncatedTo(ChronoUnit.MILLIS))).isTrue();
        UpdateRentDTO updateRentDTO2 = new UpdateRentDTO(LocalDateTime.now().minusDays(1),LocalDateTime.now().minusWeeks(3L));
        Assertions.assertThatThrownBy(() -> rentManager.updateRent(rent1.getRentId(),updateRentDTO2)).isInstanceOf(IllegalOperationException.class);
        UpdateRentDTO rent2 = new UpdateRentDTO(LocalDateTime.now().minusWeeks(2L),LocalDateTime.now().plusWeeks(3L));
        Assertions.assertThatThrownBy(() -> rentManager.updateRent(rentManager.findAllRents().get(0).getRentId(),rent2))
                .isInstanceOf(IllegalOperationException.class);


    }
    @Test
    @Transactional
    public void deleteRentTest() {
        createTestingData();
        String bufferedRentId = rentManager.findAllRents().get(0).getRentId();
        int bufferedRents = rentManager.findAllRents().size();
        var rent = rentManager.findRentById(bufferedRentId);
        int ActiveRentsSize = userManager.getActiveRents(rent.getUserId()).size();
        rentManager.deleteRent(bufferedRentId);
        Assertions.assertThat(rentManager.findAllRents().size() == bufferedRents - 1).isTrue();
        Assertions.assertThat(userManager.getActiveRents(rent.getUserId()).size() == ActiveRentsSize - 1).isTrue();
        Assertions.assertThatThrownBy(() -> rentManager.findRentById(bufferedRentId)).isInstanceOf(RecordNotFoundException.class);

    }
}

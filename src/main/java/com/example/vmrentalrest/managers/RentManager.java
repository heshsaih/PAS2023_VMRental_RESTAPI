package com.example.vmrentalrest.managers;


import com.example.vmrentalrest.exceptions.illegalOperationExceptions.CantDeleteRentException;
import com.example.vmrentalrest.exceptions.illegalOperationExceptions.ClientHasTooManyRentsException;
import com.example.vmrentalrest.exceptions.illegalOperationExceptions.UserIsNotActiveException;
import com.example.vmrentalrest.exceptions.illegalOperationExceptions.DeviceAlreadyRentedException;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.RentNotFoundException;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.UserNotFoundException;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.VirtualDeviceNotFoundException;
import com.example.vmrentalrest.model.users.Client;
import com.example.vmrentalrest.model.Rent;
import com.example.vmrentalrest.model.virtualdevices.VirtualDevice;
import com.example.vmrentalrest.repositories.UserRepository;
import com.example.vmrentalrest.repositories.RentRepository;
import com.example.vmrentalrest.repositories.VirtualDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RequiredArgsConstructor
public class RentManager {

    private final RentRepository rentRepository;
    private final UserManager userManager;
    private final UserRepository userRepository;
    private final VirtualDeviceRepository virtualDeviceRepository;

    public Rent createRent(Rent rent) throws DeviceAlreadyRentedException, ClientHasTooManyRentsException, UserIsNotActiveException, UserNotFoundException, InvalidRentException {
        if(rent == null) {
            throw new InvalidRentException();
        }
        if(rent.getUserId() == null
                || rent.getVirtualDeviceId() == null
                || rent.getStartLocalDateTime() == null
                || rent.getEndLocalDateTime() == null
                || rent.getStartLocalDateTime().isAfter(rent.getStartLocalDateTime())) {
            throw new InvalidRentException();
        }
        Client client = (Client) userManager.findUserById(rent.getUserId());
        VirtualDevice virtualDevice = virtualDeviceRepository.findById(rent.getVirtualDeviceId()).orElseThrow(VirtualDeviceNotFoundException::new);
        if(!client.isActive()) {
            throw new UserIsNotActiveException();
        }
        if(userManager.getActiveRents(client.getId()).size() >= client.getClientType().getValue()){
            throw new ClientHasTooManyRentsException();
        }
        if(!willVirtualDeviceBeRented(rent)) {
            Rent newRent = new Rent();
            newRent.setStartLocalDateTime(rent.getStartLocalDateTime());
            newRent.setEndLocalDateTime(rent.getEndLocalDateTime());
            newRent.setUserId(client.getId());
            newRent.setVirtualDeviceId(virtualDevice.getId());
            rentRepository.save(newRent);
            return newRent;
        } else {
            throw new DeviceAlreadyRentedException();
        }
    }
    public void deleteRent(String id) throws UserNotFoundException, RentNotFoundException, CantDeleteRentException {
        Rent rent = rentRepository.findById(id).orElseThrow(RentNotFoundException::new);
        if(LocalDateTime.now().isAfter(rent.getStartLocalDateTime())) {
            throw new CantDeleteRentException();
        }
        Client client = (Client) userManager.findUserById(rent.getUserId());
        userRepository.save(client);
        rentRepository.deleteById(id);
    }

    public List<Rent> findAllRents() {
        return rentRepository.findAll();
    }
    public Rent findRentById(String id) throws RentNotFoundException {
        return rentRepository.findById(id).orElseThrow(RentNotFoundException::new);
    }

    public Rent updateRent(String rentId,Rent rent) throws RentNotFoundException, CantUpdateRentException, InvalidRentException, InvalidDatesException {
        if(rent == null) {
            throw new InvalidRentException();
        }
        var value = rentRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
        //if(rent.getUserId() != null) value.setUserId(rent.getUserId());
        //if(rent.getVirtualDeviceId() != null) value.setVirtualDeviceId(rent.getVirtualDeviceId());
        if(rent.getStartLocalDateTime() != null) value.setStartLocalDateTime(rent.getStartLocalDateTime());
        if(rent.getEndLocalDateTime() != null) value.setEndLocalDateTime(rent.getEndLocalDateTime());
        if(value.getStartLocalDateTime().isAfter(value.getEndLocalDateTime())) {
            throw new InvalidDatesException();
        }
        if(willVirtualDeviceBeRented(value)) {
            throw new CantUpdateRentException();
        }
        rentRepository.save(value);
        return value;

    }
    public List<Rent> findByVirtualDeviceId(String id) {
        virtualDeviceRepository.findById(id).orElseThrow(VirtualDeviceNotFoundException::new);
        return rentRepository.findAllByVirtualDeviceId(id);
    }
    public List<Rent> findByUserId(String id) {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return rentRepository.findAllByUserId(id);
    }
    private boolean willVirtualDeviceBeRented(Rent rent1) {
        return rentRepository.existsRentByVirtualDeviceIdAndRentIdNotAndStartLocalDateTimeIsBeforeAndEndLocalDateTimeIsAfter(
                rent1.getVirtualDeviceId(),
                rent1.getRentId(),
                rent1.getStartLocalDateTime(),
                rent1.getStartLocalDateTime())
                || rentRepository.existsRentByVirtualDeviceIdAndRentIdNotAndStartLocalDateTimeIsBeforeAndEndLocalDateTimeIsAfter(
                rent1.getVirtualDeviceId(),
                rent1.getRentId(),
                rent1.getEndLocalDateTime(),
                rent1.getEndLocalDateTime())
                || rentRepository.existsRentByVirtualDeviceIdAndRentIdNotAndStartLocalDateTimeIsAfterAndEndLocalDateTimeIsBefore(
                rent1.getVirtualDeviceId(),
                rent1.getRentId(),
                rent1.getStartLocalDateTime(),
                rent1.getEndLocalDateTime());
    }
//                rentRepository.findAllByVirtualDeviceId(rent1.getVirtualDeviceId())
//                .stream()
//                .anyMatch(rent ->
//                        !rent.getRentId().equals(rent1.getRentId())
//                        &&(rent.getStartLocalDateTime().isBefore(rent1.getStartLocalDateTime()) && rent.getEndLocalDateTime().isAfter(rent1.getStartLocalDateTime())
//                        || rent.getStartLocalDateTime().isBefore(rent1.getEndLocalDateTime()) && rent.getEndLocalDateTime().isAfter(rent1.getEndLocalDateTime())
//                        || rent.getStartLocalDateTime().isAfter(rent1.getStartLocalDateTime()) && rent.getEndLocalDateTime().isBefore(rent1.getEndLocalDateTime())));
//    }
//                rentRepository.existsRentByVirtualDeviceIdAndRentIdNotAndStartLocalDateTimeIsBeforeAndEndLocalDateTimeIsAfterOrRentIdNotAndStartLocalDateTimeIsBeforeAndEndLocalDateTimeIsAfterOrRentIdNotAndStartLocalDateTimeIsAfterAndEndLocalDateTimeIsBefore(
//                rent1.getVirtualDeviceId(),
//                rent1.getRentId(),
//                rent1.getStartLocalDateTime(),
//                rent1.getStartLocalDateTime(),
//                rent1.getRentId(),
//                rent1.getEndLocalDateTime(),
//                rent1.getEndLocalDateTime(),
//                rent1.getRentId(),
//                rent1.getStartLocalDateTime(),
//                rent1.getEndLocalDateTime());

}

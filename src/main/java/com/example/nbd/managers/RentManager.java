package com.example.nbd.managers;

import com.example.nbd.exceptions.CantDeleteRentException;
import com.example.nbd.exceptions.ClientHasTooManyRentsException;
import com.example.nbd.exceptions.UserIsNotActiveException;
import com.example.nbd.exceptions.DeviceAlreadyRentedException;
import com.example.nbd.exceptions.invalidParametersExceptions.CantUpdateRentException;
import com.example.nbd.exceptions.invalidParametersExceptions.InvalidDatesException;
import com.example.nbd.exceptions.invalidParametersExceptions.InvalidRentException;
import com.example.nbd.exceptions.recordNotFoundExceptions.RentNotFoundException;
import com.example.nbd.exceptions.recordNotFoundExceptions.UserNotFoundException;
import com.example.nbd.model.users.Client;
import com.example.nbd.model.Rent;
import com.example.nbd.repositories.UserRepository;
import com.example.nbd.repositories.RentRepository;
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

    public Rent createRent(Rent rent) throws DeviceAlreadyRentedException, ClientHasTooManyRentsException, UserIsNotActiveException, UserNotFoundException, InvalidRentException {
        if(rent == null) {
            throw new InvalidRentException();
        }
        if(rent.getClientId() == null
                || rent.getVirtualDeviceId() == null
                || rent.getStartLocalDateTime() == null
                || rent.getEndLocalDateTime() == null
                || rent.getStartLocalDateTime().isAfter(rent.getStartLocalDateTime())) {
            throw new InvalidRentException();
        }
        Client client = (Client) userManager.findUserById(rent.getClientId());
        if(!client.isActive()) {
            throw new UserIsNotActiveException();
        }
        if(client.getActiveRents().size() >= client.getClientType().getValue()){
            throw new ClientHasTooManyRentsException();
        }
        if(!willVirtualDeviceBeRented(rent)) {
            Rent newRent = new Rent();
            newRent.setStartLocalDateTime(rent.getStartLocalDateTime());
            newRent.setEndLocalDateTime(rent.getEndLocalDateTime());
            newRent.setClientId(client.getId());
            newRent.setVirtualDeviceId(rent.getVirtualDeviceId());
            rentRepository.save(newRent);
            userManager.addRentToCurrentRents(client, newRent);
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
        Client client = (Client) userManager.findUserById(rent.getClientId());
        client.getActiveRents().remove(rent.getRentId());
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
            if(rent.getClientId() != null) value.setClientId(rent.getClientId());
            if(rent.getVirtualDeviceId() != null) value.setVirtualDeviceId(rent.getVirtualDeviceId());
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
    private boolean willVirtualDeviceBeRented(Rent rent1) {
        return rentRepository.findAllByVirtualDeviceId(rent1.getVirtualDeviceId())
                .stream()
                .anyMatch(rent ->
                        !rent.getRentId().equals(rent1.getRentId())
                        &&(rent.getStartLocalDateTime().isBefore(rent1.getStartLocalDateTime()) && rent.getEndLocalDateTime().isAfter(rent1.getStartLocalDateTime())
                        || rent.getStartLocalDateTime().isBefore(rent1.getEndLocalDateTime()) && rent.getEndLocalDateTime().isAfter(rent1.getEndLocalDateTime())
                        || rent.getStartLocalDateTime().isAfter(rent1.getStartLocalDateTime()) && rent.getEndLocalDateTime().isBefore(rent1.getEndLocalDateTime())));
    }
    
}

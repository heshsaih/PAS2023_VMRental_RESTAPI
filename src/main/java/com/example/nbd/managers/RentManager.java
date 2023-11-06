package com.example.nbd.managers;

import com.example.nbd.exceptions.ClientHasTooManyRentsException;
import com.example.nbd.exceptions.UserIsNotActiveException;
import com.example.nbd.exceptions.DeviceAlreadyRentedException;
import com.example.nbd.exceptions.invalidParametersExceptions.InvalidDatesException;
import com.example.nbd.exceptions.recordNotFoundExceptions.RentNotFoundException;
import com.example.nbd.exceptions.recordNotFoundExceptions.UserNotFoundException;
import com.example.nbd.model.users.Client;
import com.example.nbd.model.Rent;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.repositories.UserRepository;
import com.example.nbd.repositories.RentRepository;
import com.example.nbd.repositories.VirtualDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RequiredArgsConstructor
public class RentManager {

    private final RentRepository rentRepository;
    private final UserManager userManager;
    private final UserRepository userRepository;

    public Rent createRent(Rent rent) throws DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, UserIsNotActiveException, UserNotFoundException {
        if(rent.getClientId() == null
                || rent.getVirtualDeviceId() == null
                || rent.getStartLocalDateTime() == null
                || rent.getEndLocalDateTime() == null
                || rent.getStartLocalDateTime().isAfter(rent.getStartLocalDateTime())) {
            throw new InvalidDatesException();
        }
        Client client = (Client) userManager.findUserById(rent.getClientId());
        if(!client.isActive()) {
            throw new UserIsNotActiveException();
        }
        if(client.getActiveRents().size() >= client.getClientType().getValue()){
            throw new ClientHasTooManyRentsException();
        }
        if(!willVirtualDeviceBeRented(rent.getVirtualDeviceId(), rent.getStartLocalDateTime(), rent.getEndLocalDateTime())) {
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
    public void endRent(Rent rent) throws UserNotFoundException {
        ((Client) userManager.findUserById(rent.getClientId())).getActiveRents().remove(rent);
        rent.setEndLocalDateTime(LocalDateTime.now());
        userRepository.findById(rent.getClientId()).ifPresent(client -> {
            ((Client) client).getActiveRents().remove(rent.getRentId());
            userRepository.save(client);
        });
    }
    public void deleteRent(String id) throws UserNotFoundException, RentNotFoundException {
        AtomicBoolean userFlag = new AtomicBoolean(false);
        AtomicBoolean rentFlag = new AtomicBoolean(true);
        rentRepository.findById(id).ifPresent(rent -> {
            rentFlag.set(false);
            try {
                ((Client) userManager.findUserById(rent.getClientId())).getActiveRents().remove(rent);
                userRepository.save(userManager.findUserById(rent.getClientId()));
            } catch (UserNotFoundException e) {
                userFlag.set(true);
                throw new RuntimeException();
            }
            rentRepository.deleteById(id);
        });
        if(rentFlag.get()) {
            throw new RentNotFoundException();
        }else if(userFlag.get()) {
            throw new UserNotFoundException();
        }
    }
    public List<Rent> findAllRents() {
        return rentRepository.findAll();
    }
    public Rent findRentById(String id) throws RentNotFoundException {
        return rentRepository.findById(id).orElseThrow(RentNotFoundException::new);
    }

    public Rent updateRent(Rent rent) throws RentNotFoundException {
        var rentOpt = rentRepository.findById(rent.getRentId());
        rentOpt.ifPresent(value -> {
            value.setClientId(rent.getClientId());
            value.setVirtualDeviceId(rent.getVirtualDeviceId());
            value.setStartLocalDateTime(rent.getStartLocalDateTime());
            value.setEndLocalDateTime(rent.getEndLocalDateTime());
            rentRepository.save(value);
        });
        return rentRepository.findById(rent.getRentId()).orElseThrow(RentNotFoundException::new);
    }
    public void updateEndLocalDateTime(String id, LocalDateTime endLocalDateTime) throws DeviceAlreadyRentedException, RentNotFoundException {
        Rent rent = rentRepository.findById(id).orElseThrow(RentNotFoundException::new);
        if(!willVirtualDeviceBeRented(rent.getVirtualDeviceId(),rent.getStartLocalDateTime(),endLocalDateTime)){
            rent.setEndLocalDateTime(endLocalDateTime);
            rentRepository.save(rent);
        } else {
            throw new DeviceAlreadyRentedException();
        }
    }
    private boolean willVirtualDeviceBeRented(String virtualDeviceId, LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
        return rentRepository.findAllByVirtualDeviceId(virtualDeviceId)
                .stream()
                .filter(rent -> (
                    rent.getStartLocalDateTime().isEqual(startLocalDateTime)
                    || rent.getStartLocalDateTime().isBefore(startLocalDateTime) && rent.getEndLocalDateTime().isAfter(startLocalDateTime)
                    || rent.getStartLocalDateTime().isBefore(endLocalDateTime) && rent.getEndLocalDateTime().isAfter(endLocalDateTime)
                    || rent.getStartLocalDateTime().isAfter(startLocalDateTime) && rent.getEndLocalDateTime().isBefore(endLocalDateTime)
                )).count() != 0;
    }
    
}

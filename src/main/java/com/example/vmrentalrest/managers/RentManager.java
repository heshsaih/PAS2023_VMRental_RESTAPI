package com.example.vmrentalrest.managers;


import com.example.vmrentalrest.CustomValidator;
import com.example.vmrentalrest.dto.updatedto.UpdateRentDTO;
import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.exceptions.IllegalOperationException;
import com.example.vmrentalrest.exceptions.RecordNotFoundException;
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

    private final CustomValidator customValidator;
    private final RentRepository rentRepository;
    private final UserManager userManager;
    private final UserRepository userRepository;
    private final VirtualDeviceRepository virtualDeviceRepository;

    public Rent createRent(Rent rent) throws IllegalOperationException{
        if(rent == null) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.BODY_IS_NULL_MESSAGE);
        }
        customValidator.validate(rent);
        if(rent.getStartLocalDateTime().isAfter(rent.getStartLocalDateTime())) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.INVALID_DATES_MESSAGE);
        }
        Client client = (Client) userManager.findUserById(rent.getUserId());
        VirtualDevice virtualDevice = virtualDeviceRepository.findById(rent.getVirtualDeviceId()).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.VIRTUAL_DEVICE_NOT_FOUND_MESSAGE));
        if(!client.isActive()) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USER_IS_NOT_ACTIVE_MESSAGE);
        }
        if(userManager.getActiveRents(client.getId()).size() >= client.getClientType().getValue()){
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.CLIENT_HAS_TOO_MANY_RENTS_MESSAGE);
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
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.DEVICE_ALREADY_RENTED_MESSAGE);
        }
    }
    public void deleteRent(String rentId) {
        checkIfCanDeleteRent(rentId);
        rentRepository.deleteById(rentId);
    }
    public void deleteRent(String rentId, String userId) {
        if(!checkIfCanDeleteRent(rentId).getUserId().equals(userId)) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USERID_DOES_NOT_MATCH_RENT_USERID_MESSAGE);
        }
        rentRepository.deleteById(rentId);
    }

    private Rent checkIfCanDeleteRent(String rentId) {
        Rent rent = rentRepository.findById(rentId).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.RENT_NOT_FOUND_MESSAGE));
        if(LocalDateTime.now().isAfter(rent.getStartLocalDateTime())) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.CANT_DELETE_RENT_MESSAGE);
        }
        return rent;
    }

    public List<Rent> findAllRents() {
        return rentRepository.findAll();
    }
    public Rent findRentById(String id) {
        return rentRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.RENT_NOT_FOUND_MESSAGE));
    }

    public Rent updateRent(String rentId, UpdateRentDTO updateRentDTO) {
        return rentRepository.save(performUpdateRent(rentId,updateRentDTO));
    }

    public Rent updateRent(String rentId, UpdateRentDTO updateRentDTO, String userId) {
        var value = performUpdateRent(rentId,updateRentDTO);
        if(!value.getUserId().equals(userId)) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USERID_DOES_NOT_MATCH_RENT_USERID_MESSAGE);
        }
        return rentRepository.save(value);
    }

    private Rent performUpdateRent(String rentId,UpdateRentDTO updateRentDTO) {
        if(updateRentDTO == null) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.BODY_IS_NULL_MESSAGE);
        }
        var value = rentRepository.findById(rentId).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.RENT_NOT_FOUND_MESSAGE));
        if(updateRentDTO.startLocalDateTime() != null) {
            value.setStartLocalDateTime(updateRentDTO.startLocalDateTime());
        }
        if(updateRentDTO.endLocalDateTime() != null) {
            value.setEndLocalDateTime(updateRentDTO.endLocalDateTime());
        }
        if(value.getStartLocalDateTime().isAfter(value.getEndLocalDateTime())) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.DATES_ARE_NOT_VALID_MESSAGE);
        }
        if(willVirtualDeviceBeRented(value)) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.DEVICE_ALREADY_RENTED_MESSAGE);
        }
        return value;
    }
    public List<Rent> findByVirtualDeviceId(String id) {
        virtualDeviceRepository.findById(id).orElseThrow(
                ()-> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.VIRTUAL_DEVICE_NOT_FOUND_MESSAGE));
        return rentRepository.findAllByVirtualDeviceId(id);
    }
    public List<Rent> findByUserId(String id) {
        userRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND_MESSAGE)
        );
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

    public Rent findRentById(String id, String userId) {
        var value = rentRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.RENT_NOT_FOUND_MESSAGE));
        if (!value.getUserId().equals(userId)) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USERID_DOES_NOT_MATCH_RENT_USERID_MESSAGE);
        }
        return value;
    }

}

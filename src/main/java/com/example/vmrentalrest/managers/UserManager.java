package com.example.vmrentalrest.managers;

import com.example.vmrentalrest.dto.updatedto.UpdateUserDTO;
import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.exceptions.illegalOperationExceptions.DuplicateRecordException;
import com.example.vmrentalrest.exceptions.illegalOperationExceptions.IllegalOperationException;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.UserNotFoundException;
import com.example.vmrentalrest.model.enums.UserType;
import com.example.vmrentalrest.model.users.Administrator;
import com.example.vmrentalrest.model.users.Client;
import com.example.vmrentalrest.model.Rent;
import com.example.vmrentalrest.model.users.ResourceManager;
import com.example.vmrentalrest.model.users.User;
import com.example.vmrentalrest.repositories.RentRepository;
import com.example.vmrentalrest.repositories.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RequiredArgsConstructor
public class UserManager {
    private final Validator validator;


    private final UserRepository userRepository;
    private final RentRepository rentRepository;

    public Administrator createAdministrator(@Valid Administrator administrator) throws IllegalOperationException {
        checkIfExistsAndSave(administrator);
        return administrator;
    }
    public ResourceManager createResourceManager(@Valid ResourceManager resourceManager) throws IllegalOperationException {
        checkIfExistsAndSave(resourceManager);
        return resourceManager;
    }
    public Client createClient(@Valid Client client) throws IllegalOperationException {
        checkIfExistsAndSave(client);
        return client;
    }
    public List<Rent> getActiveRents(String id) {
        return rentRepository.findRentsByUserIdAndEndLocalDateTimeIsAfter(id, LocalDateTime.now());
    }
    private void checkIfExistsAndSave(User user) throws DuplicateRecordException {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USER_ALREADY_EXISTS_MESSAGE);
        }
        userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public User findUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User updateUser(String id, UpdateUserDTO user) throws UserNotFoundException {

        var userOpt = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if(user.firstName() != null){
            userOpt.setFirstName(user.firstName());
        }
        if(user.lastName() != null){
            userOpt.setLastName(user.lastName());
        }
        if(user.address() != null) {
            userOpt.setAddress(user.address());
        }
        userRepository.save(userOpt);
        return userOpt;
    }
    public void setActive(String id) throws UserNotFoundException {
        var value = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
            if(!value.isActive()) {
                value.setActive(true);
                userRepository.save(value);
            }
    }
    public void setInactive(String id) throws UserNotFoundException {
        var value = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
            if(value.isActive()) {
                value.setActive(false);
                userRepository.save(value);
            }
    }
    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }
    public ArrayList<User> findAllByUsernameContainsIgnoreCase(String username) {
        return userRepository.findAllByUsernameContainsIgnoreCase(username);
    }
    public ArrayList<Rent> findUserActiveRents(String userID) {
        return rentRepository.findRentsByUserIdAndEndLocalDateTimeIsAfter(userID,LocalDateTime.now());
    }
}

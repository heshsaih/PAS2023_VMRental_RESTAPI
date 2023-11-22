package com.example.vmrentalrest.managers;

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
        userRepository.save(administrator);
        return administrator;
    }
    public ResourceManager createResourceManager(@Valid ResourceManager resourceManager) throws IllegalOperationException {
        userRepository.save(resourceManager);
        return resourceManager;
    }
    public Client createClient(@Valid Client client) throws IllegalOperationException {
        userRepository.save(client);
        return client;
    }

    public User createUser(@Valid User user, UserType userType) throws DuplicateRecordException, UnknownUserTypeException, InvalidUserException {
      //  validator.validate(user).forEach(violation -> {
     //       System.out.println(violation.getMessage());
     //   });
        if(userType == null){
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USER_TYPE_IS_NULL_MESSAGE);
        } else if(!isUserValid(user,userType)){
            throw new InvalidUserException();
        }
        switch (userType) {
            case ADMINISTRATOR -> {
                Administrator administrator = new Administrator();
                setUserProperties(administrator,user);
                userRepository.save(administrator);
                return administrator;
            }
            case CLIENT -> {
                Client client = new Client();
                setUserProperties(client,user);
                client.setClientType(((Client) user).getClientType());
                userRepository.save(client);
                return client;
            }
            case RESOURCE_MANAGER -> {
                ResourceManager resourceManager = new ResourceManager();
                setUserProperties(resourceManager,user);
                userRepository.save(resourceManager);
                return resourceManager;

            }
            default -> throw new UnknownUserTypeException();
        }
    }
    private boolean isUserValid(User user, UserType userType) {
        try{
            if(StringUtils.hasText(user.getUsername())
                && StringUtils.hasText(user.getFirstName())
                && StringUtils.hasText(user.getLastName())
                && StringUtils.hasText(user.getAddress().getCity())
                && StringUtils.hasText(user.getAddress().getStreet())
                && StringUtils.hasText(user.getAddress().getHouseNumber())
                && StringUtils.hasText(user.getUsername())){
                switch (userType) {
                    case ADMINISTRATOR, RESOURCE_MANAGER -> {
                        return true;
                    }
                    case CLIENT -> {
                        return ((Client) user).getClientType() != null;
                    }
                }
            }
        } catch (NullPointerException e){
        }
        return false;
    }
    public List<Rent> getActiveRents(String id) {
        return rentRepository.findRentsByUserIdAndEndLocalDateTimeIsAfter(id, LocalDateTime.now());
    }
    private void setUserProperties(User nonAbstractUser,User user) throws DuplicateRecordException {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USER_ALREADY_EXISTS_MESSAGE);
        }
        nonAbstractUser.setUsername(user.getUsername());
        nonAbstractUser.setActive(true);
        nonAbstractUser.setAddress(user.getAddress());
        nonAbstractUser.setFirstName(user.getFirstName());
        nonAbstractUser.setLastName(user.getLastName());
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public User findUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User updateUser(String id, User user) throws UserNotFoundException {

        var userOpt = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if(user.getFirstName() != null
                && StringUtils.hasText(user.getFirstName())){
            userOpt.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null
                && StringUtils.hasText(user.getLastName())){
            userOpt.setLastName(user.getLastName());
        }
        if(user.getAddress() != null
                && user.getAddress().getCity() != null
                && StringUtils.hasText(user.getAddress().getCity())
                && user.getAddress().getStreet() != null
                && StringUtils.hasText(user.getAddress().getStreet())
                && user.getAddress().getHouseNumber() != null
                && StringUtils.hasText(user.getAddress().getHouseNumber())) {
            userOpt.setAddress(user.getAddress());
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

package com.example.vmrentalrest.managers;

import com.example.vmrentalrest.exceptions.DuplicateRecordException;
import com.example.vmrentalrest.exceptions.invalidParametersExceptions.InvalidUserException;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.UserNotFoundException;
import com.example.vmrentalrest.exceptions.invalidParametersExceptions.UnknownUserTypeException;
import com.example.vmrentalrest.model.enums.UserType;
import com.example.vmrentalrest.model.users.Administrator;
import com.example.vmrentalrest.model.users.Client;
import com.example.vmrentalrest.model.Rent;
import com.example.vmrentalrest.model.users.ResourceManager;
import com.example.vmrentalrest.model.users.User;
import com.example.vmrentalrest.repositories.RentRepository;
import com.example.vmrentalrest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RequiredArgsConstructor
public class UserManager {


    private final UserRepository userRepository;
    private final RentRepository rentRepository;


    public User createUser(User user,UserType userType) throws DuplicateRecordException, UnknownUserTypeException, InvalidUserException {
        if(userType == null){
            throw new UnknownUserTypeException();
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
                client.setActiveRents(new ArrayList<>());
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
    public void removeFromActiveRents(String clientId) throws UserNotFoundException {
        userRepository.findById(clientId).ifPresent(client -> {
            ((Client) client).setActiveRents(((Client) client).getActiveRents()
                    .stream()
                    .filter(value -> rentRepository
                            .findById(value).get()
                            .getEndLocalDateTime()
                            .isBefore(LocalDateTime.now()))
                    .collect(Collectors.toList()));
            userRepository.save(client);
        });
    }
    private void setUserProperties(User nonAbstractUser,User user) throws DuplicateRecordException {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new DuplicateRecordException();
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
    public Client addRentToCurrentRents(Client client, Rent rent) throws UserNotFoundException {
      var userOpt =  userRepository.findById(client.getId());
      userOpt.ifPresent(value -> {
          ((Client) value).getActiveRents().add(rent.getRentId());
          userRepository.save(value);
      });
      return (Client) userOpt.orElseThrow(UserNotFoundException::new);
    }
    public User updateUser(String id, User user) throws UserNotFoundException {
        var userOpt = userRepository.findById(id);
        userOpt.ifPresent(value -> {
            if(user.getFirstName() != null
                    && StringUtils.hasText(user.getFirstName())){
                value.setFirstName(user.getFirstName());
            }
            if(user.getLastName() != null
                    && StringUtils.hasText(user.getLastName())){
                value.setLastName(user.getLastName());
            }
            if(user.getAddress() != null
                    && user.getAddress().getCity() != null
                    && StringUtils.hasText(user.getAddress().getCity())
                    && user.getAddress().getStreet() != null
                    && StringUtils.hasText(user.getAddress().getStreet())
                    && user.getAddress().getHouseNumber() != null
                    && StringUtils.hasText(user.getAddress().getHouseNumber())) {
                value.setAddress(user.getAddress());
            }
            if(value instanceof Client
                    && ((Client) user).getClientType() != null
                    && !((Client) user).getClientType().equals(((Client) value).getClientType())) {
                ((Client) value).setClientType(((Client) user).getClientType());
            }
            userRepository.save(value);
        });
        return userOpt.orElseThrow(UserNotFoundException::new);
    }
    public User setActive(String id) throws UserNotFoundException {
        var userOpt = userRepository.findById(id);
        userOpt.ifPresent(value -> {
            if(!value.isActive()) {
                value.setActive(true);
                userRepository.save(value);
            }
        });
        return userOpt.orElseThrow(UserNotFoundException::new);
    }
    public User setInactive(String id) throws UserNotFoundException {
        var userOpt = userRepository.findById(id);
        userOpt.ifPresent(value -> {
            if(value.isActive()) {
                value.setActive(false);
                userRepository.save(value);
            }
        });
        return userOpt.orElseThrow(UserNotFoundException::new);
    }
    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }
    public ArrayList<User> findAllByUsernameContainsIgnoreCase(String username) {
        return userRepository.findAllByUsernameContainsIgnoreCase(username);
    }
}

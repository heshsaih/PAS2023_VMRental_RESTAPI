package com.example.nbd.managers;

import com.example.nbd.exceptions.DuplicateRecordException;
import com.example.nbd.exceptions.UnknownUserTypeException;
import com.example.nbd.model.enums.ClientType;
import com.example.nbd.model.enums.UserType;
import com.example.nbd.model.users.Administrator;
import com.example.nbd.model.users.Client;
import com.example.nbd.model.Rent;
import com.example.nbd.model.users.ResourceManager;
import com.example.nbd.model.users.User;
import com.example.nbd.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RequiredArgsConstructor
public class UserManager {


    private final UserRepository userRepository;


    public User createUser(User user,UserType userType) throws DuplicateRecordException, UnknownUserTypeException {
        if(userType == null){
            throw new UnknownUserTypeException();
        }
        switch (userType) {
            case ADMINISTRATOR -> {
                Administrator administrator = new Administrator();
                setUserProperties(administrator,user);
                userRepository.save(user);
                return administrator;
            }
            case CLIENT -> {
                Client client = new Client();
                setUserProperties(client,user);
                client.setClientType(ClientType.BRONZE);
                userRepository.save(user);
                return client;
            }
            case RESOURCE_MANAGER -> {
                ResourceManager resourceManager = new ResourceManager();
                setUserProperties(resourceManager,user);
                userRepository.save(user);
                return resourceManager;

            }
            default -> throw new UnknownUserTypeException();
        }
    }
    private void setUserProperties(User nonAbstractUser,User user) throws DuplicateRecordException {
        if(userRepository
                .existsByFirstNameAndLastNameAndAddress_CityAndAddress_StreetAndAddress_HouseNumber(
                         user.getFirstName()
                        ,user.getLastName()
                        ,user.getAddress().getCity()
                        ,user.getAddress().getStreet()
                        ,user.getAddress().getHouseNumber())){
            throw new DuplicateRecordException();
        }
        nonAbstractUser.setActive(true);
        nonAbstractUser.setAddress(user.getAddress());
        nonAbstractUser.setFirstName(user.getFirstName());
        nonAbstractUser.setLastName(user.getLastName());
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public User findUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }
    public Client addRentToCurrentRents(Client client, Rent rent) {
      var clientOpt =  userRepository.findById(client.getId());
      clientOpt.ifPresent(value -> {
          ((Client) value).getActiveRents().add(rent.getRentId());
          userRepository.save(value);
      });
      return client;
    }
    public User updateUser(String id, User user) {
        var clientOpt = userRepository.findById(id);
        clientOpt.ifPresent(value -> {
            value.setFirstName(user.getFirstName());
            value.setLastName(user.getLastName());
            value.setAddress(user.getAddress());
            if(value instanceof Client){
                ((Client) value).setClientType(((Client) user).getClientType());
            }
            userRepository.save(value);
        });
        return user;
    }
    public User setActive(String id) {
        var userOpt = userRepository.findById(id);
        userOpt.ifPresent(value -> {
            value.setActive(!value.isActive());
            userRepository.save(value);
        });
        return userOpt.orElse(null);
    }
    public User setInactive(String id) {
        var userOpt = userRepository.findById(id);
        userOpt.ifPresent(value -> {
            value.setActive(false);
            userRepository.save(value);
        });
        return userOpt.orElse(null);
    }
}

package com.example.vmrentalrest.managers;

import com.example.vmrentalrest.CustomValidator;
import com.example.vmrentalrest.dto.updatedto.UpdateUserDTO;
import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.exceptions.RecordNotFoundException;
import com.example.vmrentalrest.exceptions.IllegalOperationException;
import com.example.vmrentalrest.model.enums.ClientType;
import com.example.vmrentalrest.model.users.Administrator;
import com.example.vmrentalrest.model.users.Client;
import com.example.vmrentalrest.model.Rent;
import com.example.vmrentalrest.model.users.ResourceManager;
import com.example.vmrentalrest.model.users.User;
import com.example.vmrentalrest.repositories.RentRepository;
import com.example.vmrentalrest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RequiredArgsConstructor
public class UserManager {

    private final CustomValidator customValidator;
    private final UserRepository userRepository;
    private final RentRepository rentRepository;
    private final PasswordEncoder passwordEncoder;


    public Administrator createAdministrator(Administrator administrator) throws IllegalOperationException {
        checkIfExistsAndValidAndSave(administrator);
        return administrator;
    }
    public ResourceManager createResourceManager(ResourceManager resourceManager) throws IllegalOperationException {
        checkIfExistsAndValidAndSave(resourceManager);
        return resourceManager;
    }
    public Client createClient(Client client) throws IllegalOperationException {
        checkIfExistsAndValidAndSave(client);
        return client;
    }
    public List<Rent> getActiveRents(String id) {
        var client = findUserById(id);
        if(client instanceof Client) {
            return rentRepository.findRentsByUserIdAndEndLocalDateTimeIsAfter(id,LocalDateTime.now());
        }
        throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USER_IS_NOT_A_CLIENT_MESSAGE);
    }
    private void checkIfExistsAndValidAndSave(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USER_ALREADY_EXISTS_MESSAGE);
        }
        customValidator.validate(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public User findUserById(String id)  {
        return userRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND_MESSAGE));
    }

    public User updateUser(String id, UpdateUserDTO user) {
        var userOpt = userRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND_MESSAGE));
        if(user == null) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.BODY_IS_NULL_MESSAGE);
        }
        if(user.firstName() != null){
            userOpt.setFirstName(user.firstName());
        }
        if(user.lastName() != null){
            userOpt.setLastName(user.lastName());
        }
        if(user.email() != null){
            userOpt.setEmail(user.email());
        }
        if(user.password() != null){
            userOpt.setPassword(user.password());
        }
        if(user.address() != null) {
            userOpt.setAddress(user.address());
        }

        customValidator.validate(userOpt);
        if(user.password() != null) {
            userOpt.setPassword(passwordEncoder.encode(userOpt.getPassword()));
        }
        userRepository.save(userOpt);
        return userOpt;
    }

    public void setActive(String id) {
        var value = userRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND_MESSAGE));
            if(!value.isActive()) {
                value.setActive(true);
                userRepository.save(value);
            }
    }

    public void setInactive(String id) {
        var value = userRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND_MESSAGE));
            if(value.isActive()) {
                value.setActive(false);
                userRepository.save(value);
            }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND_MESSAGE));
    }
    public ArrayList<User> findAllByUsernameContainsIgnoreCase(String username) {
        return userRepository.findAllByUsernameContainsIgnoreCase(username);
    }

    public void updateClientType(String id, ClientType clientType) {
        var client = findUserById(id);
        if(client instanceof Client) {
            ((Client) client).setClientType(clientType);
            userRepository.save(client);
            return;
        }
        throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.USER_IS_NOT_A_CLIENT_MESSAGE);
    }
    public UserDetailsService getUserDetailsService() {
        return username -> userRepository.findByUsername(username).orElseThrow(
                () -> new RecordNotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND_MESSAGE));
    }


}
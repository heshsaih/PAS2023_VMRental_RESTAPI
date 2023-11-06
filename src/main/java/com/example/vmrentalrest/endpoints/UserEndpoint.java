package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.exceptions.RestExceptionHandler;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.UserNotFoundException;
import com.example.vmrentalrest.managers.UserManager;
import com.example.vmrentalrest.model.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserManager userManager;

    @GetMapping
    public List<User> getAllUsers(){
        return userManager.findAllUsers();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String id) throws UserNotFoundException {
            return userManager.findUserById(id);
    }

}

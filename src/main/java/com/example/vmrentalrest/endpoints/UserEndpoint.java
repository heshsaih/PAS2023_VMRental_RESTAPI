package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.managers.UserManager;
import com.example.vmrentalrest.model.enums.UserType;
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
    public User getUserById(@PathVariable String id) {
            userManager.removeFromActiveRents(id);
            return userManager.findUserById(id);
    }
    @GetMapping("getbyusername")
    public User getUserByUsername(@RequestParam String username) {
        return userManager.findByUsername(username);
    }
    @GetMapping("/getbyusernamecontains/{username}")
    public List<User> getUsersByUsernameContains(@PathVariable String username) {
        return userManager.findAllByUsernameContainsIgnoreCase(username);
    }
    @PostMapping
    public User createUser(@RequestBody User user, @RequestParam UserType userType) {
        return userManager.createUser(user,userType);
    }
    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @RequestBody User user) {
        userManager.updateUser(id,user);
    }
    @PostMapping("/{id}/isactive")
    public void changeUserActivity(@PathVariable String id, @RequestParam boolean setStateTo) {
        if (setStateTo) {
            userManager.setActive(id);
        } else {
            userManager.setInactive(id);
        }
    }
}

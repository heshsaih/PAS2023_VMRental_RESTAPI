package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.dto.UserDTO;
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
    @GetMapping("/getbyusername")
    public User getUserByUsername(@RequestParam String username) {
        return userManager.findByUsername(username);
    }
    @GetMapping("/getbyusernamecontains/{username}")
    public List<User> getUsersByUsernameContains(@PathVariable String username) {
        return userManager.findAllByUsernameContainsIgnoreCase(username);
    }
    @PostMapping
    public User createUser(@RequestBody UserDTO userDTO) {
        return userManager.createUser(userDTO.convertToUser(),userDTO.getUserType());
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return userManager.updateUser(id,userDTO.convertToUser(),userDTO.getUserType());
    }
    @PatchMapping("/{id}/activate")
    public void activateUser(@PathVariable String id) {
        userManager.setActive(id);
    }
    @PatchMapping("/{id}/deactivate")
    public void deactivateUser(@PathVariable String id) {
        userManager.setInactive(id);
    }
}

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
    public List<UserDTO> getAllUsers(){
        return userManager.findAllUsers()
                .stream()
                .map(UserDTO::new)
                .toList();
    }
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable String id) {
            userManager.removeFromActiveRents(id);
            return new UserDTO(userManager.findUserById(id));
    }
    @GetMapping("/getbyusername")
    public UserDTO getUserByUsername(@RequestParam String username) {
        return new UserDTO(userManager.findByUsername(username));
    }
    @GetMapping("/getbyusernamecontains/{username}")
    public List<UserDTO> getUsersByUsernameContains(@PathVariable String username) {
        return userManager.findAllByUsernameContainsIgnoreCase(username)
                .stream()
                .map(UserDTO::new)
                .toList();
    }
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return new UserDTO(userManager.createUser(userDTO.convertToUser(),userDTO.getUserType()));
    }
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return new UserDTO(userManager.updateUser(id,userDTO.convertToUser(),userDTO.getUserType()));
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

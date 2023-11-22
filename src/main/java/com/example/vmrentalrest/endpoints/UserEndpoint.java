package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.dto.getuserdto.GetUserDTO;
import com.example.vmrentalrest.dto.createuserdto.CreateAdminDTO;
import com.example.vmrentalrest.managers.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserManager userManager;

    @GetMapping
    public List<GetUserDTO> getAllUsers(){
        return userManager.findAllUsers()
                .stream()
                .map(GetUserDTO::new)
                .toList();
    }
    @GetMapping("/{id}")
    public GetUserDTO getUserById(@PathVariable String id) {
            return new GetUserDTO(userManager.findUserById(id));
    }
    @GetMapping("/getbyusername")
    public GetUserDTO getUserByUsername(@RequestParam String username) {
        return new GetUserDTO(userManager.findByUsername(username));
    }
    @GetMapping("/getbyusernamecontains/{username}")
    public List<GetUserDTO> getUsersByUsernameContains(@PathVariable String username) {
        return userManager.findAllByUsernameContainsIgnoreCase(username)
                .stream()
                .map(GetUserDTO::new)
                .toList();
    }
    @PostMapping
    public GetUserDTO createUser(@RequestBody GetUserDTO getUserDTO) {
        return new GetUserDTO(userManager.createUser(getUserDTO.convertToUser(), getUserDTO.getUserType()));
    }
    @PostMapping("/createadministrator")
    public GetUserDTO createAdministrator(@RequestBody CreateAdminDTO createAdminDTO) {
        return new GetUserDTO(userManager.createAdministrator(userDTO.convertToUser()));
    }
    @PutMapping("/{id}")
    public GetUserDTO updateUser(@PathVariable String id, @RequestBody GetUserDTO getUserDTO) {
        return new GetUserDTO(userManager.updateUser(id, getUserDTO.convertToUser(), getUserDTO.getUserType()));
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

package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.dto.createuserdto.CreateClientDTO;
import com.example.vmrentalrest.dto.createuserdto.CreateResourceManagerDTO;
import com.example.vmrentalrest.dto.getuserdto.GetAdministratorDTO;
import com.example.vmrentalrest.dto.getuserdto.GetClientDTO;
import com.example.vmrentalrest.dto.getuserdto.GetResourceManagerDTO;
import com.example.vmrentalrest.dto.getuserdto.GetUserDTO;
import com.example.vmrentalrest.dto.createuserdto.CreateAdministratorDTO;
import com.example.vmrentalrest.dto.updatedto.UpdateUserDTO;
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
    public List<GetUserDTO> getAllUsers(){
        return userManager.findAllUsers()
                .stream()
                .map(User::convertToDTO)
                .toList();
    }
    @GetMapping("/{id}")
    public GetUserDTO getUserById(@PathVariable String id) {
            return userManager.findUserById(id).convertToDTO();
    }
    @GetMapping("/getbyusername")
    public GetUserDTO getUserByUsername(@RequestParam String username) {
        return userManager.findByUsername(username).convertToDTO();
    }
    @GetMapping("/getbyusernamecontains/{username}")
    public List<GetUserDTO> getUsersByUsernameContains(@PathVariable String username) {
        return userManager.findAllByUsernameContainsIgnoreCase(username)
                .stream()
                .map(User::convertToDTO)
                .toList();
    }

    @PostMapping("/createadministrator")
    public GetAdministratorDTO createAdministrator(@RequestBody CreateAdministratorDTO createAdministratorDTO) {
        return new GetAdministratorDTO(userManager.createAdministrator(createAdministratorDTO.createAdministratorFromDTO()));
    }
    @PostMapping("/createclient")
    public GetClientDTO createClient(@RequestBody CreateClientDTO createClientDTO) {
        return userManager.createClient(createClientDTO.createClientFromDTO()).convertToDTO();
    }
    @PostMapping("/createresourcemanager")
    public GetResourceManagerDTO createResourceManager(@RequestBody CreateResourceManagerDTO createResourceManagerDTO) {
        return userManager.createResourceManager(createResourceManagerDTO.createResourceManagerFromDTO()).convertToDTO();
    }


    @PutMapping("/{id}")
    public GetUserDTO updateUser(@PathVariable String id, @RequestBody UpdateUserDTO updateUserDTO) {
        return userManager.updateUser(id, updateUserDTO).convertToDTO();
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

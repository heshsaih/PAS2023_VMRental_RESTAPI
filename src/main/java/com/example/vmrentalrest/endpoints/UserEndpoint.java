package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.security.JwsService;
import com.example.vmrentalrest.security.JwtService;
import com.example.vmrentalrest.dto.SignInDTO;
import com.example.vmrentalrest.dto.createuserdto.CreateClientDTO;
import com.example.vmrentalrest.dto.createuserdto.CreateResourceManagerDTO;
import com.example.vmrentalrest.dto.getuserdto.GetAdministratorDTO;
import com.example.vmrentalrest.dto.getuserdto.GetClientDTO;
import com.example.vmrentalrest.dto.getuserdto.GetResourceManagerDTO;
import com.example.vmrentalrest.dto.getuserdto.GetUserDTO;
import com.example.vmrentalrest.dto.createuserdto.CreateAdministratorDTO;
import com.example.vmrentalrest.dto.updatedto.UpdateUserDTO;
import com.example.vmrentalrest.security.AuthenticationService;
import com.example.vmrentalrest.managers.UserManager;
import com.example.vmrentalrest.security.JwtAuthenticationResponse;
import com.example.vmrentalrest.model.Rent;
import com.example.vmrentalrest.model.enums.ClientType;
import com.example.vmrentalrest.model.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserManager userManager;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final JwsService jwsService;

    @GetMapping
    public ResponseEntity<List<GetUserDTO>> getAllUsers(){
        return ResponseEntity.ok()
                .body(userManager.findAllUsers()
                .stream()
                .map(User::convertToDTO)
                .toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> getUserById(@PathVariable String id) {
            return ResponseEntity.ok()
                    .body(userManager.findUserById(id).convertToDTO());
    }
    @GetMapping("/getbyusername")
    public ResponseEntity<GetUserDTO> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok()
                .body(userManager.findByUsername(username).convertToDTO());
    }
    @GetMapping("/getbyusernamecontains/{username}")
    public ResponseEntity<List<GetUserDTO>> getUsersByUsernameContains(@PathVariable String username) {
        return ResponseEntity.ok()
                .body(userManager.findAllByUsernameContainsIgnoreCase(username)
                .stream()
                .map(User::convertToDTO)
                .toList());
    }

    @PostMapping("/createadministrator")
    public ResponseEntity<GetAdministratorDTO> createAdministrator(@RequestBody CreateAdministratorDTO createAdministratorDTO) {
        return ResponseEntity.ok()
                .body(new GetAdministratorDTO(userManager.createAdministrator(createAdministratorDTO.createAdministratorFromDTO())));
    }
    @PostMapping("/createclient")
    public ResponseEntity<GetClientDTO> createClient(@RequestBody CreateClientDTO createClientDTO) {
        return ResponseEntity.ok()
                .body(userManager.createClient(createClientDTO.createClientFromDTO()).convertToDTO());
    }
    @PostMapping("/createresourcemanager")
    public ResponseEntity<GetResourceManagerDTO> createResourceManager(@RequestBody CreateResourceManagerDTO createResourceManagerDTO) {
        return ResponseEntity.ok()
                .body(userManager.createResourceManager(createResourceManagerDTO.createResourceManagerFromDTO())
                        .convertToDTO());
    }


    @PutMapping("/{id}")
    public ResponseEntity<GetUserDTO> updateUser(@PathVariable String id, @RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok()
                .body(userManager.updateUser(id, updateUserDTO).convertToDTO());
    }
    @PatchMapping("/{id}/updateclienttype")
    public void updateClientType(@PathVariable String id, @RequestParam ClientType clientType) {
        userManager.updateClientType(id, clientType);
    }
    @GetMapping("/{id}/getactiverents")
    public ResponseEntity<List<Rent>> getActiveRents(@PathVariable String id) {
        return ResponseEntity.ok()
                .body(userManager.getActiveRents(id));
    }
    @PatchMapping("/{id}/activate")
    public void activateUser(@PathVariable String id) {
        userManager.setActive(id);
    }
    @PatchMapping("/{id}/deactivate")
    public void deactivateUser(@PathVariable String id) {
        userManager.setInactive(id);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok()
                .eTag(jwsService.generateSign(userManager.findByUsername(signInDTO.getUsername()).getId()))
                .body(authenticationService.signIn(signInDTO));
    }
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody CreateClientDTO createClientDTO) {
        return ResponseEntity.ok()
                .body(authenticationService.signUp(createClientDTO));

    }
    @GetMapping("/self")
    public ResponseEntity<GetUserDTO> getSelf(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok()
                .body(userManager.findUserById(jwtService.extractUserId(token)).convertToDTO());
    }
    @PutMapping("/self")
    public ResponseEntity<GetUserDTO> updateSelf(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok()
                .body(userManager.updateUser(jwtService.extractUserId(token), updateUserDTO).convertToDTO());
    }
    @PostMapping("/self/signout")
    public void signOut(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        jwtService.addTokenToBlackList(token);
    }
}

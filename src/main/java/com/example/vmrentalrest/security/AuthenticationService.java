package com.example.vmrentalrest.security;

import com.example.vmrentalrest.dto.SignInDTO;
import com.example.vmrentalrest.dto.createuserdto.CreateClientDTO;
import com.example.vmrentalrest.managers.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserManager userManager;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public JwtAuthenticationResponse signIn(SignInDTO signInDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDTO.getUsername(), signInDTO.getPassword()));
        //Gdyby nie dzialalo logowanie odkomentowac to i zakomentowac wczesniejsza linijkę
        // authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDTO.getUsername(), passwordEncoder.encode(signInDTO.getPassword())));
        var client = userManager.findByUsername(signInDTO.getUsername());
        return new JwtAuthenticationResponse(jwtService.generateToken(client));
    }

    public JwtAuthenticationResponse signUp(CreateClientDTO createClientDTO) {
        var client = createClientDTO.createClientFromDTO();
        return new JwtAuthenticationResponse(jwtService.generateToken(client));
    }
}

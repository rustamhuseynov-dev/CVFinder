package com.rustam.CVFinder.controller;

import com.rustam.CVFinder.dto.TokenDTO;
import com.rustam.CVFinder.dto.request.AuthRequest;
import com.rustam.CVFinder.dto.request.LoginRequest;
import com.rustam.CVFinder.dto.response.AuthResponse;
import com.rustam.CVFinder.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping(path = "/create-user")
    public ResponseEntity<AuthResponse> createUser(@Valid @RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(authService.createUser(authRequest), HttpStatus.CREATED);
    }

    @PostMapping(path = "/create-hr")
    public ResponseEntity<AuthResponse> createHumanResource(@Valid @RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(authService.createHumanResource(authRequest),HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authService.login(loginRequest),HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/logout")
    public ResponseEntity<String> logout(@RequestBody String refreshToken){
        return new ResponseEntity<>(authService.logout(refreshToken),HttpStatus.ACCEPTED);
    }
}

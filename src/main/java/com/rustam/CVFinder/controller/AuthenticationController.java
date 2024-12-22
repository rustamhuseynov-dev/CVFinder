package com.rustam.CVFinder.controller;

import com.rustam.CVFinder.dto.request.AuthRequest;
import com.rustam.CVFinder.dto.response.AuthResponse;
import com.rustam.CVFinder.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

package com.rustam.CVFinder.service;

import com.rustam.CVFinder.dao.entity.HumanResource;
import com.rustam.CVFinder.dao.entity.User;
import com.rustam.CVFinder.dao.entity.enums.Role;
import com.rustam.CVFinder.dao.repository.AuthRepository;
import com.rustam.CVFinder.dto.request.AuthRequest;
import com.rustam.CVFinder.dto.response.AuthResponse;
import com.rustam.CVFinder.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    public AuthResponse createUser(AuthRequest authRequest) {
        User user = User.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .email(authRequest.getEmail())
                .authorities(Collections.singleton(Role.User))
                .enabled(true)
                .phone(authRequest.getPhone())
                .name(authRequest.getName())
                .build();
        authRepository.save(user);
        return authMapper.toDto(user);
    }

    public AuthResponse createHumanResource(AuthRequest authRequest) {
        HumanResource humanResource = HumanResource.builder()
                .name(authRequest.getName())
                .email(authRequest.getEmail())
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .authorities(Collections.singleton(Role.Human_Resource))
                .enabled(true)
                .phone(authRequest.getPhone())
                .build();
        authRepository.save(humanResource);
        return authMapper.toDto(humanResource);
    }
}

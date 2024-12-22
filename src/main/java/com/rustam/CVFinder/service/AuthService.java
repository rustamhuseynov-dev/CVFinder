package com.rustam.CVFinder.service;

import com.rustam.CVFinder.dao.entity.BaseUser;
import com.rustam.CVFinder.dao.entity.HumanResource;
import com.rustam.CVFinder.dao.entity.User;
import com.rustam.CVFinder.dao.entity.enums.Role;
import com.rustam.CVFinder.dao.repository.AuthRepository;
import com.rustam.CVFinder.dto.TokenDTO;
import com.rustam.CVFinder.dto.TokenPair;
import com.rustam.CVFinder.dto.request.AuthRequest;
import com.rustam.CVFinder.dto.request.LoginRequest;
import com.rustam.CVFinder.dto.response.AuthResponse;
import com.rustam.CVFinder.exception.custom.IncorrectPasswordException;
import com.rustam.CVFinder.mapper.AuthMapper;
import com.rustam.CVFinder.util.UtilService;
import com.rustam.CVFinder.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final UtilService utilService;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String,String> redisTemplate;

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

    public TokenDTO login(LoginRequest loginRequest) {
        BaseUser baseUser = utilService.findByUsername(loginRequest.getUsername());
        if (!passwordEncoder.matches(loginRequest.getPassword(),baseUser.getPassword())){
            throw new IncorrectPasswordException("password does not match");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(baseUser.getId()));
        TokenPair tokenPair = userDetails.isEnabled() ?
                TokenPair.builder()
                        .accessToken(jwtUtil.createToken(String.valueOf(baseUser.getId())))
                        .refreshToken(jwtUtil.createRefreshToken(String.valueOf(baseUser.getId())))
                        .build()
                : new TokenPair();  // Boş tokenlər üçün
        String redisKey = "refresh_token:" + baseUser.getId();
        redisTemplate.opsForValue().set(redisKey, tokenPair.getRefreshToken(), Duration.ofDays(2)); // 2 gün müddətinə saxla
        return TokenDTO.builder()
                .tokenPair(tokenPair)
                .build();
    }
}

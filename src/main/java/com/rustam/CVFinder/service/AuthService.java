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
import com.rustam.CVFinder.dto.request.RefreshTokenRequest;
import com.rustam.CVFinder.dto.request.UpdateRequest;
import com.rustam.CVFinder.dto.response.AuthResponse;
import com.rustam.CVFinder.dto.response.UpdateResponse;
import com.rustam.CVFinder.exception.custom.ExistsException;
import com.rustam.CVFinder.exception.custom.IncorrectPasswordException;
import com.rustam.CVFinder.exception.custom.UnauthorizedException;
import com.rustam.CVFinder.mapper.AuthMapper;
import com.rustam.CVFinder.util.UtilService;
import com.rustam.CVFinder.util.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

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

    public String logout(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken().trim();
        refreshToken = refreshToken.replace("\"", "");
        String userId = jwtUtil.getUserIdAsUsernameFromToken(refreshToken);
        String redisKey = "refresh_token:" + userId;
        Boolean delete = redisTemplate.delete(redisKey);
        if (Boolean.TRUE.equals(delete)){
            return "The refresh token was deleted and the user was logged out.";
        }
        else {
            throw new UnauthorizedException("An error occurred while logging out.");
        }
    }

    public String refreshToken(RefreshTokenRequest request) {
        System.out.println(request.getRefreshToken());
        String refreshToken = request.getRefreshToken().trim();
        refreshToken = refreshToken.replace("\"", "");
        String userId = jwtUtil.getUserIdAsUsernameFromTokenExpired(refreshToken);
        if (userId == null) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        String redisKey = "refresh_token:" + userId;
        String storedRefreshToken = redisTemplate.opsForValue().get(redisKey);
        if (storedRefreshToken != null) {
            return storedRefreshToken;
        } else {
            throw new UnauthorizedException("Invalid refresh token");
        }
    }

    public UpdateResponse updateUser(UpdateRequest updateRequest) {
        String currentUsername = utilService.getCurrentUsername();
        User user = (User) utilService.findById(updateRequest.getId());
        utilService.validation(currentUsername, user.getId());
        boolean exists = utilService.findAllBy().stream()
                .map(User::getUsername)
                .anyMatch(existingUsername -> existingUsername.equals(updateRequest.getUsername()));
        if (exists) {
            throw new ExistsException("This username is already taken.");
        }
        user.setName(updateRequest.getName());
        user.setUsername(updateRequest.getUsername());
        user.setEmail(updateRequest.getEmail());
        user.setPhone(updateRequest.getPhone());
        authRepository.save(user);
        return authMapper.toUpdateResponse(user);
    }

    public UpdateResponse updateHumanResource(UpdateRequest updateRequest) {
        String currentUsername = utilService.getCurrentUsername();
        User user = (User) utilService.findById(updateRequest.getId());
        utilService.validation(currentUsername, user.getId());
        boolean exists = utilService.findAllBy().stream()
                .map(User::getUsername)
                .anyMatch(existingUsername -> existingUsername.equals(updateRequest.getUsername()));
        if (exists) {
            throw new ExistsException("This username is already taken.");
        }
        user.setName(updateRequest.getName());
        user.setUsername(updateRequest.getUsername());
        user.setEmail(updateRequest.getEmail());
        user.setPhone(updateRequest.getPhone());
        authRepository.save(user);
        return authMapper.toUpdateResponse(user);
    }
}

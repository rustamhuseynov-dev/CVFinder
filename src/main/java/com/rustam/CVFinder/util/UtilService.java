package com.rustam.CVFinder.util;

import com.rustam.CVFinder.dao.entity.BaseUser;
import com.rustam.CVFinder.dao.entity.User;
import com.rustam.CVFinder.dao.repository.AuthRepository;
import com.rustam.CVFinder.exception.custom.BaseUserNotFoundException;
import com.rustam.CVFinder.exception.custom.InvalidUUIDFormatException;
import com.rustam.CVFinder.exception.custom.NoAuthotiryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UtilService {

    private final AuthRepository authRepository;

    public BaseUser findByUsername(String username) {
        return authRepository.findByUsername(username)
                .orElseThrow(() -> new BaseUserNotFoundException("No such user found."));
    }

    public UUID convertToUUID(String id) {
        try {
            log.info("id {}",id);
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDFormatException("Invalid UUID format for ID: " + id, e);
        }
    }

    public BaseUser findById(UUID id) {
        return authRepository.findById(id)
                .orElseThrow(() -> new BaseUserNotFoundException("No such user found."));
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public void validation(String currentUsername, String id) {
        if (!currentUsername.equals(id)){
            throw new NoAuthotiryException("You have no authority.");
        }
    }

    public List<User> findAllBy(){
        return authRepository.findAllBy();
    }
}

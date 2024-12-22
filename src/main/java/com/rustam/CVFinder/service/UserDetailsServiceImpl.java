package com.rustam.CVFinder.service;

import com.rustam.CVFinder.dao.entity.BaseUser;
import com.rustam.CVFinder.dao.entity.User;
import com.rustam.CVFinder.dao.repository.AuthRepository;
import com.rustam.CVFinder.exception.custom.BaseUserNotFoundException;
import com.rustam.CVFinder.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthRepository authRepository;
    private final UtilService utilService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseUser user = authRepository.findById(utilService.convertToUUID(username))
                .orElseThrow(() -> new BaseUserNotFoundException("User Not Found with username: " + username));

        return new CustomUserDetails(user);
    }
}

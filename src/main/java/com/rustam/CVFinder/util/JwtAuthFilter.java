package com.rustam.CVFinder.util;

import com.rustam.CVFinder.service.UserDetailsServiceImpl;
import com.rustam.CVFinder.util.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.getTokenFromRequest(request);

        if (token != null && jwtUtil.validateToken(token)) {
            String userIdAsUsername = jwtUtil.getUserIdAsUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userIdAsUsername);

            if (userDetails != null/* && utilService.getCurrentUserByEmailForAllRole(email).isActivated()*/) {
                System.out.println("UserDetails of current user: " + userDetails);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}

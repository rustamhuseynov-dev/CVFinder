package com.rustam.CVFinder.dto.response;

import com.rustam.CVFinder.dao.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String name;
    private String username;
    private String email;
    private Role role;
    private boolean enabled;
}

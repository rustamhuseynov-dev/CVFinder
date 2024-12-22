package com.rustam.CVFinder.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {

    private String name;
    private String email;
    private String username;
    private String password;
    private String phone;

}
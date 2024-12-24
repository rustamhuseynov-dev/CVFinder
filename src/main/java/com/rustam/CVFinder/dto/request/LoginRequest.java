package com.rustam.CVFinder.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "The username column cannot be empty.")
    private String username;
    @NotBlank(message = "The password column cannot be empty.")
    private String password;
}

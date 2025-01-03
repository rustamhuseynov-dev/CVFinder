package com.rustam.CVFinder.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {

    @NotBlank(message = "The name column cannot be empty.")
    private String name;
    @Pattern(regexp = "[a-z]+@[a-z]+\\.[a-z]{2,4}", message = "Enter your email address.")
    private String email;
    @NotBlank(message = "The username column cannot be empty.")
    private String username;
    @NotBlank(message = "The password column cannot be empty.")
    private String password;
    @Pattern(regexp = "\\(\\d{3}\\)-\\d{3}-\\d{4}", message = "The phone number must be in the format (000)-000-0000.")
    private String phone;

}

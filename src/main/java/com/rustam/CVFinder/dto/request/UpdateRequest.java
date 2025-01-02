package com.rustam.CVFinder.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRequest {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String phone;
}

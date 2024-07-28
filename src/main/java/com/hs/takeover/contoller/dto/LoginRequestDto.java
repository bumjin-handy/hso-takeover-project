package com.hs.takeover.contoller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Simple DTO representing a login request to a remote service.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;

    public LoginRequestDto() {
    }
}

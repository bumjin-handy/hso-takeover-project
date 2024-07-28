package com.hs.takeover.contoller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
    private String message;
    private String username;
    private String K;

}

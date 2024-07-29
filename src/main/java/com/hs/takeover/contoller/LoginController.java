package com.hs.takeover.contoller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hs.takeover.api.dto.GWOpenApiResponseDto;
import com.hs.takeover.api.service.GWOpenApiLoginService;
import com.hs.takeover.contoller.dto.LoginRequestDto;
import com.hs.takeover.contoller.dto.LoginResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private GWOpenApiLoginService gwOpenApiLoginService;

    @PostMapping("/takeover/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        log.debug("username=" + loginRequest.getUsername() + ", password=" + loginRequest.getPassword());
        GWOpenApiResponseDto gWOpenApiResponseDto = gwOpenApiLoginService.requestLogin(loginRequest.getUsername(), loginRequest.getPassword());
        if (isValidUser(gWOpenApiResponseDto)) {
            LoginResponseDto response = new LoginResponseDto("로그인 성공", gWOpenApiResponseDto.getUname(), gWOpenApiResponseDto.getKey());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("로그인 실패: 잘못된 사용자 이름 또는 비밀번호");
        }
    }

    private boolean isValidUser(GWOpenApiResponseDto gWOpenApiResponseDto) {
        log.debug("GWOpenApiResponseDto K=" + gWOpenApiResponseDto.getKey());
        return StringUtils.isNotBlank(gWOpenApiResponseDto.getKey());
    }
}




package com.hs.takeover.contoller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hs.takeover.api.dto.GWOpenApiResponseDto;
import com.hs.takeover.api.service.GWOpenApiLoginService;
import com.hs.takeover.contoller.dto.LoginRequestDto;


@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean private GWOpenApiLoginService gwOpenApiLoginService;

    @DisplayName("[view][POST] 로그인 - 정상 호출")
    @Test
    void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
        // Given
        String expectedUserName = "testUser";
        String expectedKey = "12345";
        String exptectedMessage = "로그인 성공";

        LoginRequestDto loginRequest = new LoginRequestDto("testUser", "testPassword");
        GWOpenApiResponseDto gwOpenApiResponseDto = GWOpenApiResponseDto.builder()
                                                        .key(expectedKey)
                                                        .uname(expectedUserName).build();
        ResponseEntity<GWOpenApiResponseDto> responseEntity = ResponseEntity.ok(gwOpenApiResponseDto);

        when (gwOpenApiLoginService.requestLogin(eq(expectedUserName), eq("testPassword")))
                .thenReturn(responseEntity.getBody());

        // When & Then
        mockMvc.perform(post("/takeover/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.k").exists())
                .andExpect(jsonPath("$.username").value(expectedUserName))
                .andExpect(jsonPath("$.message").value(exptectedMessage));

        then(gwOpenApiLoginService).should().requestLogin(eq(expectedUserName), eq("testPassword"));
    }
}
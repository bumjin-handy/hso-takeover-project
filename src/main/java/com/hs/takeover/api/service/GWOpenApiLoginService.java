package com.hs.takeover.api.service;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.hs.takeover.api.dto.GWOpenApiResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class GWOpenApiLoginService {
    private final RestTemplate restTemplate;
    private final GWOpenApiUriBuilderService gwOpenApiUriBuilderService;

    public GWOpenApiResponseDto requestLogin(String username, String password) {
        if(ObjectUtils.isEmpty(username)) return null;
        if(ObjectUtils.isEmpty(password)) return null;

        URI uri = gwOpenApiUriBuilderService.buildUriByUserNameAndPassword(username, password);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity<>(headers);
        // kakao api 호출
        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, GWOpenApiResponseDto.class).getBody();
        // POST 요청 보내기 및 응답 받기
        /*
         * ResponseEntity<String> response = restTemplate.exchange( uri,
         * HttpMethod.POST, httpEntity, String.class );
         *
         * // 응답 상태 코드 출력 System.out.println("Status code: " +
         * response.getStatusCode());
         *
         * // 응답 헤더 출력 System.out.println("Response headers: " + response.getHeaders());
         *
         * // 응답 본문 출력 System.out.println("Response body: " + response.getBody());
         */
    }


}

package com.hs.takeover.api.service;

import java.io.StringReader;
import java.net.URI;

import javax.xml.transform.stream.StreamSource;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.InputSource;

import com.hs.takeover.api.dto.GWErrorResponseDto;
import com.hs.takeover.api.dto.GWOpenApiResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class GWOpenApiLoginService {
    private final RestTemplate restTemplate;
    private final GWOpenApiUriBuilderService gwOpenApiUriBuilderService;

    public Object requestLogin(String username, String password) {
        if (ObjectUtils.isEmpty(username)) return null;
        if (ObjectUtils.isEmpty(password)) return null;

        URI uri = gwOpenApiUriBuilderService.buildUriByUserNameAndPassword(username, password);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity<>(headers);

        Object response = exchangeWithXml(uri, HttpMethod.POST, httpEntity);

        if (response instanceof GWOpenApiResponseDto) {
            GWOpenApiResponseDto successResponse = (GWOpenApiResponseDto) response;
            // 성공 응답 처리
            System.out.println("Session ID: " + successResponse.getId());
        } else if (response instanceof GWErrorResponseDto) {
            GWErrorResponseDto errorResponse = (GWErrorResponseDto) response;
            // 에러 응답 처리
            System.out.println("Error code: " + errorResponse.getCode());
            System.out.println("Error message: " + errorResponse.getMessage());
        } else {
            throw new RuntimeException("Unexpected response type");
        }
        // POST 요청 보내기 및 응답 받기
        /*
        ResponseEntity<String> response = restTemplate.exchange(uri,
                HttpMethod.POST, httpEntity, String.class);
        // 응답 상태 코드 출력
        System.out.println("Status code: " +
                response.getStatusCode());
        // 응답 헤더 출력
        System.out.println("Response headers: " + response.getHeaders());

        // 응답 본문 출력
        System.out.println("Response body: " + response.getBody());
        return null;
        */

        return response;
    }

    public Object exchangeWithXml(URI uri, HttpMethod method, HttpEntity<?> httpEntity) {
        ResponseEntity<String> response = restTemplate.exchange(uri, method, httpEntity, String.class);
        String xmlResponse = response.getBody();
        System.out.println(xmlResponse);
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(GWOpenApiResponseDto.class, GWErrorResponseDto.class);

        try {
            InputSource inputSource = new InputSource(new StringReader(xmlResponse));

            StringReader reader = new StringReader(xmlResponse);
            Object result = marshaller.unmarshal(new StreamSource(reader));

            // Check the type of the unmarshalled object and return it accordingly
            if (result instanceof GWOpenApiResponseDto) {
                return (GWOpenApiResponseDto) result;
            } else if (result instanceof GWErrorResponseDto) {
                return (GWErrorResponseDto) result;
            } else {
                throw new IllegalStateException("Unexpected response type: " + result.getClass().getName());
            }
        } catch (Exception e) {
            // Log any other unexpected exceptions
            log.error("Unexpected error during XML unmarshalling", e);
            throw new RuntimeException("Failed to unmarshal XML response", e);
        }
    }

}

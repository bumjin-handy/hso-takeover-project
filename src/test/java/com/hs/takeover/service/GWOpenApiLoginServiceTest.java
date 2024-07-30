package com.hs.takeover.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.StringWriter;
import java.net.URI;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hs.takeover.api.dto.GWErrorResponseDto;
import com.hs.takeover.api.dto.GWOpenApiResponseDto;
import com.hs.takeover.api.service.GWOpenApiLoginService;
import com.hs.takeover.api.service.GWOpenApiUriBuilderService;

class GWOpenApiLoginServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GWOpenApiUriBuilderService gwOpenApiUriBuilderService;

    @InjectMocks
    private GWOpenApiLoginService gwOpenApiLoginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("정상적인 ID, PASSWORD를 넣은 경우")
    @Test
    void testRequestLoginWithValidCredentials() throws Exception {
        String username = "testUser";
        String password = "validPassword";
        URI mockUri = URI.create("http://localhost:8080/login");
        GWOpenApiResponseDto mockResponse = GWOpenApiResponseDto.builder()
        		.key("12345")
        		.uname("username")
        		.empcode("emp12345")
        		.deptid("000000000")
        		.build();
        
        JAXBContext jaxbContext = JAXBContext.newInstance(GWOpenApiResponseDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        marshaller.marshal(mockResponse, sw);
        String expectedMockResponseXmlString = sw.toString();
        System.out.println(expectedMockResponseXmlString);
        //
        when(gwOpenApiUriBuilderService.buildUriByUserNameAndPassword(username, password)).thenReturn(mockUri);
        when(restTemplate.exchange(eq(mockUri), 
        			eq(HttpMethod.POST), 
        			any(HttpEntity.class), 
        			eq(String.class)))
                .thenReturn(ResponseEntity.ok(expectedMockResponseXmlString));

        GWOpenApiResponseDto result = (GWOpenApiResponseDto)gwOpenApiLoginService.requestLogin(username, password);

        assertNotNull(result);
        verify(gwOpenApiUriBuilderService).buildUriByUserNameAndPassword(username, password);
        verify(restTemplate).exchange(eq(mockUri), 
        		eq(HttpMethod.POST), 
        		any(HttpEntity.class), 
        		eq(String.class));
    }
    
    @DisplayName("비정상적인 ID, PASSWORD를 넣은 경우")
    @Test
    void testRequestInvalidLoginWithValidCredentials() throws Exception {
        String username = "testUser";
        String password = "invalidPassword";
        URI mockUri = URI.create("http://localhost:8080/login");
        GWErrorResponseDto mockResponse = GWErrorResponseDto.builder()
        		.code("102")
        		.message("존재하지 않는 사용자")
        		.build();
        
        JAXBContext jaxbContext = JAXBContext.newInstance(GWErrorResponseDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        marshaller.marshal(mockResponse, sw);
        String expectedMockResponseXmlString = sw.toString();
        System.out.println(expectedMockResponseXmlString);
        //
        when(gwOpenApiUriBuilderService.buildUriByUserNameAndPassword(username, password)).thenReturn(mockUri);
        when(restTemplate.exchange(eq(mockUri), 
        			eq(HttpMethod.POST), 
        			any(HttpEntity.class), 
        			eq(String.class)))
                .thenReturn(ResponseEntity.ok(expectedMockResponseXmlString));

        GWErrorResponseDto result = (GWErrorResponseDto)gwOpenApiLoginService.requestLogin(username, password);

        assertNotNull(result);
        verify(gwOpenApiUriBuilderService).buildUriByUserNameAndPassword(username, password);
        verify(restTemplate).exchange(eq(mockUri), 
        		eq(HttpMethod.POST), 
        		any(HttpEntity.class), 
        		eq(String.class));
    }

    @Test
    void testRequestLoginWithEmptyUsername() {
        assertNull(gwOpenApiLoginService.requestLogin("", "password"));
    }

    @Test
    void testRequestLoginWithEmptyPassword() {
        assertNull(gwOpenApiLoginService.requestLogin("username", ""));
    }
}

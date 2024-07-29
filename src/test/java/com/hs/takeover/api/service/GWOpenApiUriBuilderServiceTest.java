package com.hs.takeover.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GWOpenApiUriBuilderServiceTest {

    private final String ssohost = "http://example.com";
    private final GWOpenApiUriBuilderService gwOpenApiUriBuilderService = new GWOpenApiUriBuilderService(ssohost);

    @BeforeEach
    void setUp() {
        //when(ssohost).thenReturn("http://example.com");
    }

    @Test
    void buildUriByUserNameAndPassword_ShouldReturnCorrectUri() {
        // Given
        String username = "testUser";
        String password = "testPassword";

        // When
        URI result = gwOpenApiUriBuilderService.buildUriByUserNameAndPassword(username, password);

        // Then
        String expectedUri = "http://example.com/jsp/openapi/OpenApi.jsp?target=session&todo=login&name=testUser&passwd=testPassword";
        assertEquals(expectedUri, result.toString());
    }
}
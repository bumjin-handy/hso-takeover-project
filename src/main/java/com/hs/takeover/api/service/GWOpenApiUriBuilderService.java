package com.hs.takeover.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class GWOpenApiUriBuilderService {
    private static final String GW_OPENAPI_URL = "/jsp/openapi/OpenApi.jsp";

    private final String ssohost;

    public URI buildUriByUserNameAndPassword(String username, String password) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(ssohost + GW_OPENAPI_URL);
        uriComponentsBuilder.queryParam("target", "session");
        uriComponentsBuilder.queryParam("todo", "login");
        uriComponentsBuilder.queryParam("name", username);
        uriComponentsBuilder.queryParam("passwd", password);

        URI uri = uriComponentsBuilder.encode().build().toUri();
        log.info("GWOpenApiUriBuilderService buildUriByUserNameAndPassword] username: {}, uri: {}", username, uri);

        return uri;
    }
}

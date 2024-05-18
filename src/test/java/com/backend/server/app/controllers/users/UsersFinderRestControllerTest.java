package com.backend.server.app.controllers.users;

import com.backend.server.contexts.shared.infrastructure.utils.UserDataUtil;
import com.backend.server.contexts.users.domain.clazz.Login;
import com.backend.server.contexts.users.domain.dto.AccessTokenSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersFinderRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @BeforeEach
    public void setup() {
        String loginUrl = "http://localhost:" + port + "/api/v1/doLogin";
        Login loginData = new Login(UserDataUtil.create().getEmail(), UserDataUtil.create().getPassword());
        ResponseEntity<AccessTokenSerializer> response = restTemplate.postForEntity(loginUrl, loginData, AccessTokenSerializer.class);

        assertThat(HttpStatus.OK == response.getStatusCode()).isTrue();
        assertThat(response.getBody()).isNotNull();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + response.getBody().getToken());
    }

    @Test
    public void find_all_users_success() {
        String url = "http://localhost:" + port + "/api/v1/users/findAll";

        HttpEntity<String> requestHeader = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestHeader, String.class);
        assertThat(HttpStatus.OK == response.getStatusCode()).isTrue();
    }
}

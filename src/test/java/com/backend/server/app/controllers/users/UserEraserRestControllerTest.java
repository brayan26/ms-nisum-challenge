package com.backend.server.app.controllers.users;

import com.backend.server.contexts.shared.infrastructure.utils.UserDataUtil;
import com.backend.server.contexts.users.domain.UserMother;
import com.backend.server.contexts.users.domain.clazz.Login;
import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.domain.dto.AccessTokenSerializer;
import com.backend.server.contexts.users.domain.dto.UserSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserEraserRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private StringBuilder url;
    private HttpHeaders headers;

    @BeforeEach
    public void setup() {
        url = new StringBuilder();
        url.append("http://localhost:").append(port).append("/api/v1/users/delete/");

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
    public void delete_user_with_error_404() {
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Void> response = restTemplate.exchange(url.append("123xyz").toString(), HttpMethod.DELETE, request, Void.class);
        assertThat(HttpStatus.NOT_FOUND == response.getStatusCode()).isTrue();
    }

    @Test
    public void delete_user_successfully_202() {
        User user = UserMother.random();
        user.setId(UUID.randomUUID().toString());
        user.setEmail("delete@gmail.com");

        String createUserUrl = "http://localhost:" + port + "/api/v1/users/add";
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<UserSerializer> createdResponse = restTemplate.postForEntity(createUserUrl, request, UserSerializer.class);
        assertThat(HttpStatus.CREATED == createdResponse.getStatusCode()).isTrue();
        assertThat(createdResponse.getBody()).isNotNull();

        HttpEntity<Void> requestHeader = new HttpEntity<>(headers);
        ResponseEntity<Void> response = restTemplate.exchange(url.append(createdResponse.getBody().getId()).toString(), HttpMethod.DELETE, requestHeader, Void.class);
    }
}

package com.backend.server.app.controllers.users;

import com.backend.server.contexts.shared.domain.errors.UsersError;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserCreatorRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;
    private HttpHeaders headers;

    @BeforeEach
    public void setup() {
        url = "http://localhost:" + port + "/api/v1/users/add";

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
    public void create_user_with_error_400() {
        HttpEntity<User> request = new HttpEntity<>(UserMother.random(), headers);
        ResponseEntity<UsersError> response = restTemplate.postForEntity(url, request ,UsersError.class);
        assertThat(HttpStatus.BAD_REQUEST == response.getStatusCode()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("USR-01");
    }

    @Test
    public void create_user_successfully_201() {
        User user = UserMother.random();
        user.setEmail("test@gmail.com");

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<UserSerializer> response = restTemplate.postForEntity(url, request, UserSerializer.class);
        assertThat(HttpStatus.CREATED == response.getStatusCode()).isTrue();
    }
}

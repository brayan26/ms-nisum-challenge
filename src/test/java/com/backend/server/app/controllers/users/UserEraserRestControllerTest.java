package com.backend.server.app.controllers.users;

import com.backend.server.contexts.users.domain.UserMother;
import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.domain.dto.UserSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @BeforeEach
    public void setup() {
        url = new StringBuilder();
        url.append("http://localhost:").append(port).append("/api/v1/users/");
    }

    @Test
    public void delete_user_with_error_404() {
        ResponseEntity<Void> response = restTemplate.exchange(url.append("123xyz").toString(), HttpMethod.DELETE, null, Void.class);
        assertThat(HttpStatus.NOT_FOUND == response.getStatusCode()).isTrue();
    }

    @Test
    public void delete_user_successfully_202() {
        User user = UserMother.random();
        user.setId(UUID.randomUUID().toString());
        user.setEmail("delete@gmail.com");
        ResponseEntity<UserSerializer> createdResponse = restTemplate.postForEntity(url.append("add").toString(), user, UserSerializer.class);
        assertThat(HttpStatus.CREATED == createdResponse.getStatusCode()).isTrue();
        assertThat(createdResponse.getBody()).isNotNull();

        ResponseEntity<Void> response = restTemplate.exchange(url.append(createdResponse.getBody().getId()).toString(), HttpMethod.DELETE, null, Void.class);
    }
}

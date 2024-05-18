package com.backend.server.app.controllers.users;

import com.backend.server.contexts.shared.domain.errors.UsersError;
import com.backend.server.contexts.users.domain.UserMother;
import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.domain.dto.UserSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @BeforeEach
    public void setup() {
        url = "http://localhost:" + port + "/api/v1/users/add";
    }

    @Test
    public void create_user_with_error_400() {
        ResponseEntity<UsersError> response = restTemplate.postForEntity(url, UserMother.random(), UsersError.class);
        assertThat(HttpStatus.BAD_REQUEST == response.getStatusCode()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("USR-01");
    }

    @Test
    public void create_user_successfully_201() {
        User user = UserMother.random();
        user.setEmail("test@gmail.com");
        ResponseEntity<UserSerializer> response = restTemplate.postForEntity(url, user, UserSerializer.class);
        assertThat(HttpStatus.CREATED == response.getStatusCode()).isTrue();
    }
}

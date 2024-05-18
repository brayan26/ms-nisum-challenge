package com.backend.server.app.controllers.users;

import com.backend.server.contexts.shared.domain.errors.UsersError;
import com.backend.server.contexts.users.domain.LoginMother;
import com.backend.server.contexts.users.domain.clazz.Login;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserValidatorRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void do_login_success() {
        String url = "http://localhost:" + port + "/api/v1/doLogin";
        ResponseEntity<Login> response = restTemplate.postForEntity(url, LoginMother.random(), Login.class);
        assertThat(HttpStatus.OK == response.getStatusCode()).isTrue();
    }

    @Test
    public void do_login_with_error_404() {
        String url = "http://localhost:" + port + "/api/v1/doLogin";
        Login login = LoginMother.random();
        login.setEmail("test404@gmail.com");
        ResponseEntity<UsersError> response = restTemplate.postForEntity(url, login, UsersError.class);
        assertThat(HttpStatus.NOT_FOUND == response.getStatusCode()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("USR-00");
    }

    @Test
    public void do_login_with_wrong_password() {
        String url = "http://localhost:" + port + "/api/v1/doLogin";
        Login login = LoginMother.random();
        login.setPassword("test404");
        ResponseEntity<UsersError> response = restTemplate.postForEntity(url, login, UsersError.class);
        assertThat(HttpStatus.BAD_REQUEST == response.getStatusCode()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("USR-02");
    }
}

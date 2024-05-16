package com.backend.server.contexts.users.application.update;

import com.backend.server.contexts.users.domain.UserMother;
import com.backend.server.contexts.users.domain.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class UserEraserTest {
    @Autowired
    private UserEraser userEraserUseCase;

    @BeforeEach
    public void setup(){
        userEraserUseCase = Mockito.mock(UserEraser.class);
        User user = UserMother.random();
    }

    @Test
    public void deleteOneUserById() {
        userEraserUseCase.run(UserMother.random().getId());
        Mockito.verify(userEraserUseCase).run(UserMother.random().getId());
    }
}

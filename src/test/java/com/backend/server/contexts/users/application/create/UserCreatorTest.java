package com.backend.server.contexts.users.application.create;

import com.backend.server.contexts.users.domain.UserMother;
import com.backend.server.contexts.users.domain.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.AssertionErrors;

@ExtendWith(MockitoExtension.class)
public class UserCreatorTest {
    private UserCreator userCreatorUseCase;

    @BeforeEach
    public void setup(){
        userCreatorUseCase = Mockito.mock(UserCreator.class);
        User user = UserMother.random();
        Mockito.when(userCreatorUseCase.run(user)).thenReturn(user);
    }

    @Test
    public void createNewUserSuccessfully() {
        User user = userCreatorUseCase.run(UserMother.random());
        AssertionErrors.assertEquals( "The user mock is not created", UserMother.random(), user);
        Mockito.verify(userCreatorUseCase).run(UserMother.random());
    }
}

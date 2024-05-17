package com.backend.server.contexts.users.application.create;

import com.backend.server.contexts.users.domain.UserMother;
import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.domain.dto.UserSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.AssertionErrors;

@ExtendWith(MockitoExtension.class)
public class UserCreatorTest {
    private UserCreator userCreatorUseCase;
    private final User user = UserMother.random();

    @BeforeEach
    public void setup(){
        userCreatorUseCase = Mockito.mock(UserCreator.class);
        Mockito.when(userCreatorUseCase.run(this.user)).thenReturn(this.user);
    }

    @Test
    public void createNewUserSuccessfully() {
        User user = userCreatorUseCase.run(this.user);
        AssertionErrors.assertEquals( "The user mock is not created", this.user, user);
        Mockito.verify(userCreatorUseCase).run(this.user);
    }
}

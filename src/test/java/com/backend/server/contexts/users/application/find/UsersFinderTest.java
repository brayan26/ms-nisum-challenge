package com.backend.server.contexts.users.application.find;

import com.backend.server.contexts.users.domain.UserMother;
import com.backend.server.contexts.users.domain.clazz.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.AssertionErrors;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UsersFinderTest {
    private UsersFinder usersFinderUseCase;
    private final User user = UserMother.random();

    @BeforeEach
    public void setup(){
        usersFinderUseCase = Mockito.mock(UsersFinder.class);
        Mockito.when(usersFinderUseCase.run()).thenReturn(List.of(this.user));
    }

    @Test
    public void find_all_users_success() {
        List<User> users = usersFinderUseCase.run();
        AssertionErrors.assertEquals( "user mock is not supported with a user list", List.of(this.user), users);
        Mockito.verify(usersFinderUseCase).run();
    }
}

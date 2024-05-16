package com.backend.server.contexts.users.application.find;

import com.backend.server.contexts.users.domain.UserMother;
import com.backend.server.contexts.users.domain.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UsersFinderTest {
    private UsersFinder usersFinderUseCase;

    @BeforeEach
    public void setup(){
        usersFinderUseCase = Mockito.mock(UsersFinder.class);
        User user = UserMother.random();
        Mockito.when(usersFinderUseCase.run()).thenReturn(List.of(user));
    }

    @Test
    public void find_all_users_success() {
        List<User> users = usersFinderUseCase.run();
        AssertionErrors.assertEquals( "user mock is not supported with a user list", List.of(UserMother.random()), users);
        Mockito.verify(usersFinderUseCase).run();
    }
}

package com.backend.server.contexts.users.domain;

import com.backend.server.contexts.users.domain.dto.User;

public class UserMother {
    private static User create(Long id) {
        return new User(id, "test", "test01", "1122q!");
    }

    public static User random() {
        return create(
            Long.parseLong( String.valueOf( 1L ))
        );
    }
}

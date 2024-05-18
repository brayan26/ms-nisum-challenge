package com.backend.server.contexts.shared.infrastructure.utils;

import com.backend.server.contexts.users.domain.clazz.Phone;
import com.backend.server.contexts.users.domain.clazz.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserDataUtil {
    private static final String NAME = "Carlos Parra";
    private static final String EMAIL = "cparra@gmail.com";
    private static final String PASSWORD = "1122q!";
    private static final Boolean ACTIVE = true;
    private static final Date NOW = new Date();

    public static User create() {
        return User.create(
            UUID.randomUUID().toString(),
            NAME,
            EMAIL,
            PASSWORD,
            List.of(new Phone("3040773", "1", "57")),
            ACTIVE,
            null,
            NOW,
            null,
            NOW
        );
    }
}

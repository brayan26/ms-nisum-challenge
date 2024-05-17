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
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMzQ1Njc5MCIsImVtYWlsIjoidGVzdEBtYWlsLmNvbSIsImlhdCI6MTcxNTkxNzUzNCwiZXhwIjoxNzE1OTIxMTM0fQ.GQWaNOiCe2cLEw4fRTm1Q2mFdtN1UrtPjZ7UWzWaAsU";
    private static final Date NOW = new Date();

    public static User create() {
        return User.create(
            UUID.randomUUID().toString(),
            NAME,
            EMAIL,
            PASSWORD,
            List.of(new Phone("3040773", "1", "57")),
            ACTIVE,
            TOKEN,
            NOW,
            null,
            NOW
        );
    }
}

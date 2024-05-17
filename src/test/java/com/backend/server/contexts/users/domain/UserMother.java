package com.backend.server.contexts.users.domain;

import com.backend.server.contexts.users.domain.clazz.Phone;
import com.backend.server.contexts.users.domain.clazz.User;

import java.util.List;
import java.util.UUID;
import java.util.Date;

public class UserMother {
    private static User create(String id, List<Phone> phones, Date created, Date lastLogin) {
        return new User(id, "test", "test01", "1122q!", phones, true, "jwt", created, null, lastLogin);
    }

    public static User random() {
        Date today = new Date();
        return create(
            UUID.randomUUID().toString(),
            List.of(new Phone("3024026718", "130", "57")),
            today,
            today
        );
    }
}

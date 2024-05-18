package com.backend.server.contexts.users.domain;

import com.backend.server.contexts.users.domain.clazz.Phone;
import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.domain.dto.UserSerializer;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Date;

public class UserMother {
    private static User create(List<Phone> phones, Date created, Date lastLogin) {
        return new User("cdb7778b-7ecf-41ca-8835-4f4b6cddc6c0", "Carl Parra", "cparra@gmail.com", "1122Q!", phones, true, "jwt", created, null, lastLogin);
    }

    public static User random() {
        ZoneId zoneId = ZoneId.of("America/Bogota");
        Date today = Date.from(LocalDateTime.now().atZone(zoneId).toInstant());
        return create(
            List.of(new Phone("3015639837", "5", "57")),
            today,
            today
        );
    }

    public static UserSerializer randomSerializer() {
        return new ModelMapper().map(UserMother.random(), UserSerializer.class);
    }
}

package com.backend.server.contexts.users.domain;

import com.backend.server.contexts.users.domain.clazz.Login;

public class LoginMother {
    public static Login random() {
        return new Login("cparra@gmail.com", "1122Q!");
    }
}

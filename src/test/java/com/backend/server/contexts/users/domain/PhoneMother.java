package com.backend.server.contexts.users.domain;

import com.backend.server.contexts.users.domain.clazz.Phone;

public class PhoneMother {

    public static Phone random() {
        return new Phone("3015639837", "5", "57");
    }
}

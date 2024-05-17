package com.backend.server.contexts.users.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccessTokenSerializer {
    private String token;

    public static AccessTokenSerializer create(String token) {
        return new AccessTokenSerializer(token);
    }
}

package com.backend.server.contexts.shared.domain.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticationError extends BaseError {

    public static AuthenticationError create() {
        return new AuthenticationError();
    }

    public AuthenticationError unauthorizedError() {
        this.setError("Invalid authentication");
        this.setCode("AUTH-00");
        return this;
    }

    public AuthenticationError invalidTokenError() {
        this.setError("Invalid token");
        this.setCode("AUTH-01");
        return this;
    }

    public AuthenticationError expiredTokenError() {
        this.setError("Expired token");
        this.setCode("AUTH-02");
        return this;
    }

    public AuthenticationError build() {
        return this;
    }
}

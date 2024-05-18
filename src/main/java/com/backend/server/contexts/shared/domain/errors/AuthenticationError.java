package com.backend.server.contexts.shared.domain.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticationError extends BaseError {

    public static AuthenticationError create() {
        return new AuthenticationError();
    }

    public AuthenticationError unauthorizedError() {
        this.setError("Autenticacion invalida");
        this.setCode("AUTH-00");
        return this;
    }

    public AuthenticationError invalidTokenError() {
        this.setError("Token invalido");
        this.setCode("AUTH-01");
        return this;
    }

    public AuthenticationError expiredTokenError() {
        this.setError("Token expirado");
        this.setCode("AUTH-02");
        return this;
    }

    public AuthenticationError build() {
        return this;
    }
}

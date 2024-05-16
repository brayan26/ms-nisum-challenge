package com.backend.server.contexts.shared.domain.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersError {
    private String code;
    private String error;

    public static UsersError create() {
        return new UsersError();
    }

    public UsersError notFound() {
        this.code = "USR-00";
        this.error = "User not found";
        return this;
    }

    public UsersError alreadyExists() {
        this.code = "USR-01";
        this.error = "User already exists";
        return this;
    }

    public UsersError invalidUser() {
        this.code = "USR-02";
        this.error = "Invalid user password";
        return this;
    }

    public UsersError build() {
        return this;
    }
}

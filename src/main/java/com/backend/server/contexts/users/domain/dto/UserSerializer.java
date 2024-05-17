package com.backend.server.contexts.users.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSerializer implements Serializable {
    private String id;
    private Boolean isActive;
    private String token;
    private Date created;
    private Date modified;
    private Date lastLogin;
}

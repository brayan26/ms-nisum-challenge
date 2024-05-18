package com.backend.server.contexts.shared.domain.jwt;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Payload implements Serializable {
    private List<String> aud;
    private List<String> scope;
    private Long exp;
    private List<String> authorities;
    private String jti;
    private String sub;
}

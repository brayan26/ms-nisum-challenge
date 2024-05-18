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
    public List<String> aud;
    public List<String> scope;
    public Long exp;
    public List<String> authorities;
    public String jti;
    public String client_id;
}

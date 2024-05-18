package com.backend.server.contexts.shared.infrastructure.utils;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.Header;
import com.backend.server.contexts.shared.domain.jwt.Payload;
import com.google.gson.Gson;
import lombok.Getter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

/**
 * The JWTDecoder class holds the decode method to parse a given JWT token into it's JWT representation.
 */
public final class JwtDecoderUtil {

    private final String[] parts;
    private final Header header;
    @Getter
    private final Payload payload;

    public JwtDecoderUtil(String jwt) throws JWTDecodeException {
        parts = TokenUtil.splitToken(jwt);
        final JWTParser converter = new JWTParser();
        String headerJson;
        String payloadJson;
        try {
            headerJson = StringUtils.newStringUtf8(Base64.decodeBase64(parts[0]));
            payloadJson = StringUtils.newStringUtf8(Base64.decodeBase64(parts[1]));
        } catch (NullPointerException e) {
            throw new JWTDecodeException("The UTF-8 Charset isn't initialized.", e);
        }
        header = converter.parseHeader(headerJson);
        payload = new Gson().fromJson(payloadJson, Payload.class);
    }

    public String getAlgorithm() {
        return header.getAlgorithm();
    }

    public String getType() {
        return header.getType();
    }

    public String getContentType() {
        return header.getContentType();
    }

    public String getKeyId() {
        return header.getKeyId();
    }

    public Claim getHeaderClaim(String name) {
        return header.getHeaderClaim(name);
    }

    public String getToken() {
        return String.format("%s.%s.%s", parts[0], parts[1], parts[2]);
    }
}
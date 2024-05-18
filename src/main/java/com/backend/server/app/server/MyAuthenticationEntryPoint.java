package com.backend.server.app.server;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException {
    // 401
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
  }

  @ExceptionHandler (value = {AccessDeniedException.class})
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    // 401
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Failed : " + accessDeniedException.getMessage());
  }
}

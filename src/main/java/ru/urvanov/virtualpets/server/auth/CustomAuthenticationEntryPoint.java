package ru.urvanov.virtualpets.server.auth;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.urvanov.virtualpets.server.api.domain.Result;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectWriter objectWriter = new ObjectMapper().writer();
    
    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Result loginResult = new Result(false, authException.getMessage());
        response.getWriter().write(objectWriter.writeValueAsString(loginResult));
    }

}

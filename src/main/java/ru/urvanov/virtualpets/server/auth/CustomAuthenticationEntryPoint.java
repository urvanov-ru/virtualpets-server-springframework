package ru.urvanov.virtualpets.server.auth;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.urvanov.virtualpets.shared.domain.LoginResult;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectWriter objectWriter = new ObjectMapper().writer();
    
    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        String message = authException.getMessage();
        LoginResult loginResult = new LoginResult();
        loginResult.setMessage(message);
        response.getWriter().write(objectWriter.writeValueAsString(loginResult));
    }

}

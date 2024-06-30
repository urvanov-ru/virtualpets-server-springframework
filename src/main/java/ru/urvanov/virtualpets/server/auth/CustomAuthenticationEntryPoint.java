package ru.urvanov.virtualpets.server.auth;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.urvanov.virtualpets.server.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.api.domain.Result;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectWriter objectWriter = new ObjectMapper().writer();
    
    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.SC_FORBIDDEN);
        Result loginResult = new Result(false, authException.getMessage());
        response.getWriter().write(objectWriter.writeValueAsString(loginResult));
    }

}

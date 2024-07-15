package ru.urvanov.virtualpets.server.auth;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AuthenticationEntryPoint перебрасывает на форму входа.
 * В приложении сервера виртуальных питомцев только возвращает 
 * ответ с 403 Forbidden и JSON с ошибкой.
 */
public class CustomAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    private ObjectWriter objectWriter = new ObjectMapper().writer();
    
    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        HttpStatus statusCode = HttpStatus.FORBIDDEN;
        response.setStatus(statusCode.value());
        ProblemDetail problemDetail = ProblemDetail
                .forStatus(statusCode);
        problemDetail.setDetail(
                authException.getLocalizedMessage() == null
                        ? authException.getMessage()
                        : authException
                                .getLocalizedMessage());
        response.getWriter()
                .write(objectWriter.writeValueAsString(problemDetail));
    }

}

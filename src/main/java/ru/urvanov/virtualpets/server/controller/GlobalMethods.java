package ru.urvanov.virtualpets.server.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Component
public class GlobalMethods {

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<Object> handleException(
            AuthenticationException ex, WebRequest request)
            throws Exception {
        HttpStatus statusCode = HttpStatus.FORBIDDEN;
        HttpHeaders headers = new HttpHeaders();
        ProblemDetail problemDetail = ProblemDetail
                .forStatus(statusCode);
        problemDetail.setDetail(
                ex.getLocalizedMessage() == null ? ex.getMessage()
                        : ex.getLocalizedMessage());
        return new ResponseEntity<>(problemDetail, headers, statusCode);
    }

}

package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public class ControllerBase extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Object> handleException(
            ServiceException ex, WebRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        ProblemDetail problemDetail = ProblemDetail
                .forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setDetail(
                ex.getLocalizedMessage() == null ? ex.getMessage()
                        : ex.getLocalizedMessage());
        return this.createResponseEntity(problemDetail, headers,
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}

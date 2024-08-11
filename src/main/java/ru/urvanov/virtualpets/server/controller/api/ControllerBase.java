package ru.urvanov.virtualpets.server.controller.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ru.urvanov.virtualpets.server.service.exception.IncompatibleVersionException;
import ru.urvanov.virtualpets.server.service.exception.PetNotFoundException;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;
import ru.urvanov.virtualpets.server.service.exception.UserNotFoundException;

public class ControllerBase extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Object> handleException(
            ServiceException ex, WebRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus;
        if (ex instanceof UserNotFoundException
                || ex instanceof PetNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        ProblemDetail problemDetail = ProblemDetail
                .forStatus(httpStatus);
        problemDetail.setDetail(ex.getErrorCode());
        if (ex instanceof IncompatibleVersionException ieEx) {
          problemDetail.setProperty("serverVersion",
                  ieEx.getServerVersion());
          problemDetail.setProperty("clientVersion",
                  ieEx.getClientVersion());
        }
        return this.createResponseEntity(problemDetail, headers,
                httpStatus, request);
    }
    

}

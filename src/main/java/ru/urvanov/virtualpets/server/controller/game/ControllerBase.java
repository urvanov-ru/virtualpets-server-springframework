package ru.urvanov.virtualpets.server.controller.game;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ru.urvanov.virtualpets.server.service.exception.NameIsBusyException;
import ru.urvanov.virtualpets.server.service.exception.NotEnoughPetResourcesException;
import ru.urvanov.virtualpets.server.service.exception.IncompatibleVersionException;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public class ControllerBase extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Object> handleException(
            ServiceException ex, WebRequest request) throws Exception {
        
        HttpStatus httpStatus;
        if ((ex instanceof NameIsBusyException)
                || (ex instanceof NotEnoughPetResourcesException)) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        HttpHeaders headers = new HttpHeaders();
        ProblemDetail problemDetail = ProblemDetail
                .forStatus(httpStatus);
        problemDetail.setDetail(ex.getErrorCode());
        return this.createResponseEntity(problemDetail, headers,
                httpStatus, request);
    }
    
    @ExceptionHandler(IncompatibleVersionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ProblemDetail handleException(
             IncompatibleVersionException ex, WebRequest request) throws Exception {
        ProblemDetail problemDetail = ProblemDetail
                .forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(ex.getErrorCode());
        problemDetail.setProperty("serverVersion", ex.getServerVersion());
        problemDetail.setProperty("clientVersion", ex.getClientVersion());
        return problemDetail;
    }
    


}

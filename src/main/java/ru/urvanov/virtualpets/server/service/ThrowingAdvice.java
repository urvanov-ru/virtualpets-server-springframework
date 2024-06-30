package ru.urvanov.virtualpets.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 */

import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public class ThrowingAdvice {
    private static final Logger log = LoggerFactory.getLogger(ThrowingAdvice.class);

    public void processException(Throwable ex) throws ServiceException {
        log.error("processException",ex);
        throw new ServiceException(ex.toString());
    }
}

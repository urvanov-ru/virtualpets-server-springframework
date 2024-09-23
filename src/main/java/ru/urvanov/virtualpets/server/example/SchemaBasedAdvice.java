package ru.urvanov.virtualpets.server.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.urvanov.virtualpets.server.controller.api.domain.HiddenObjectsGame;

public class SchemaBasedAdvice {

    private static final Logger logger = LoggerFactory.getLogger(SchemaBasedAdvice.class);
    
    public void beforeDaoAdvice(JoinPoint joinPoint) {
        logger.info("Accessing DAO layer {}.{}, arguments count {}.",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs().length);
    }
    
    public void afterJoinGameReturningAdvice(
            JoinPoint joinPoint,
            HiddenObjectsGame hiddenObjectsGame) {
        logger.info("""
                A player joined hidden objects game. \
                Arguments count {}. \
                Returning {}. \
                """,
                joinPoint.getArgs().length,
                hiddenObjectsGame);
    }
    
    public void afterBuildThrowingAdvice(
            JoinPoint joinPoint,
            Throwable throwableParameter) {
        logger.info("""
                    A build method thrown an exception. \
                    Throwing {} \
                    Arguments count {}. \
                    """,
                throwableParameter,
                joinPoint.getArgs().length);
    }
    
    public void afterBuildAdvice(JoinPoint joinPoint) {
        logger.info("Build finished. Arguments count {}.",
                joinPoint.getArgs().length);
    }
    
    public Object aroundOpenBoxNewbieAdvice(
            ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("We are in openBoxNewbiew around advice.");
        return proceedingJoinPoint.proceed();
    }

}
